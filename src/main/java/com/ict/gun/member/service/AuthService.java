package com.ict.gun.member.service;

import java.io.IOException;
import java.util.List;

import com.ict.gun.jwt.entity.Token;
import com.ict.gun.jwt.entity.TokenType;
import com.ict.gun.jwt.repository.TokenRepository;
import com.ict.gun.jwt.service.JwtService;
import com.ict.gun.jwt.service.UserDetailsCustom;
import com.ict.gun.member.dto.AuthResponse;
import com.ict.gun.member.entity.Member;
import com.ict.gun.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final UserDetailsCustom userDetailsCustom;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse authenticate(Member member) {
        // 사용자의 암호를 검증하기 전에 암호를 인코딩합니다.
        String encodedPassword = passwordEncoder.encode(member.getMemPw());
        System.out.println("\n\nlogin 테스트 2 : " + member.getMemEmail());
        System.out.println("\n\nlogin 테스트 2 : " + encodedPassword);

        // matches 메서드를 사용하여 비밀번호를 확인합니다.
        if (passwordEncoder.matches(member.getMemPw(), encodedPassword)) {
            // 인증이 성공하면 나머지 코드를 진행합니다.
            UserDetails userDetails = userDetailsCustom.loadUserByUsername(member.getMemEmail());
            log.info("userDetails : " + userDetails);
            String jwtToken = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            revokeAllUserTokens(member);
            saveToken(member, jwtToken);
            return new AuthResponse(jwtToken, refreshToken);
        } else {
            // 비밀번호가 일치하지 않을 경우 예외 처리 또는 다른 작업 수행
            throw new BadCredentialsException("Invalid password");
        }
    }


    private void revokeAllUserTokens(Member member) {
        List<Token> validTokens = tokenRepository.findAllValidTokenByUserId(member.getMemEmail());
        if (!validTokens.isEmpty()) {
            validTokens.forEach(t -> {
                t.setExpired(true);
                t.setRevoked(true);
            });
            tokenRepository.saveAll(validTokens);
        }
    }

    private void saveToken(Member member, String jwtToken) {
        Token token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .userId(member.getMemEmail())
                .build();
        tokenRepository.save(token);
    }

//    private UserDetails loadUserByUsername(String userEmail) {
//        // userEmail을 사용하여 데이터베이스에서 사용자 정보를 가져오는 코드
//        // 이 예시에서는 MemberRepository를 사용한다고 가정합니다.
//        Member member = memberRepository.findByUserEmail(userEmail);
//
//        // Spring Security에서 사용하는 UserDetails 객체로 변환
//        return new User(member.getUserEmail(), member.getUserPwd(), new ArrayList<>());
//    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail; // username
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            Member member = (Member) this.memberRepository.findByMemEmail(userEmail);
            if (member != null && jwtService.isTokenValid(refreshToken, (UserDetails) member)) {
                String accessToken = jwtService.generateToken((UserDetails) member);
                AuthResponse authResponse = new AuthResponse(accessToken, refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}