package com.ict.gun.member.controller;

import com.ict.gun.config.ApplicationConfig;
import com.ict.gun.member.dto.AuthRequest;
import com.ict.gun.member.dto.AuthResponse;
import com.ict.gun.member.entity.Member;
import com.ict.gun.member.service.AuthService;
import com.ict.gun.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class MemberController {
    private final MemberService memberService;
    private final ApplicationConfig passwordEncoder;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody Member member) {
        try {
            // 비밀번호 암호화
            String encodedPw = passwordEncoder.passwordEncoder().encode(member.getMemPw());
            // 회원메일 중복 체크
            Boolean isDuplicate = memberService.isDuplicate(member.getMemEmail());

            if(isDuplicate) {
                String result = "duplicate";
                return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
            }
            if(!isDuplicate) {
                // 회원가입 서비스 호출
                member.setMemPw(encodedPw);
                member.setMemType("member");
                memberService.join(member);
                log.info("member" + member);
                // 성공적으로 가입되었을 때
                String result = "success";
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            // 가입 실패 시
            String result = "fail";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> aythenticate(@RequestBody AuthRequest login){
        Member member = Member.builder()
                .memEmail(login.memEmail())
                .memPw(login.memPw())
                .build();
        System.out.println("\n\nlogin 테스트 1 : " + member.getMemEmail());
        System.out.println("\n\nlogin 테스트 1 : " + member.getMemPw());
        return ResponseEntity.ok(authService.authenticate(member));
    }
    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }
}
