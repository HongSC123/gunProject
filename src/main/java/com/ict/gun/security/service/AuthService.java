package com.ict.gun.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ict.gun.member.dto.RequestLoginDto;
import com.ict.gun.member.repository.MemberRepository;
import com.ict.gun.security.config.JwtUtil;
import com.ict.gun.security.utility.GenerateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    // private final MemberRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public ResponseEntity<String> login(RequestLoginDto param) throws JsonProcessingException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(param.getMemEmail());
        if(userDetails == null) return GenerateResponse.notFound("user not found",null);
        if(!encoder.matches(param.getMemPw(),userDetails.getPassword())) return GenerateResponse.badRequest("Password not matched",null);
        return GenerateResponse.success("Sign In Success", jwtUtil.generate(param.getMemEmail(),"ACCESS"));
    }
}
