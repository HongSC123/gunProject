package com.ict.gun.common.email.controller;

import com.ict.gun.common.email.dto.EmailPostDto;
import com.ict.gun.common.email.entity.EmailMessage;
import com.ict.gun.common.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;


    // 임시 비밀번호 발급
    @PostMapping("/password")
    public ResponseEntity sendPasswordMail(@RequestBody EmailPostDto emailPostDto) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(emailPostDto.getEmail())
                .subject("[건강을부탁해] 임시 비밀번호 발급")
                .build();

        emailService.sendMail(emailMessage, "password");

        return ResponseEntity.ok().build();
    }

    // 회원가입 이메일 인증 - 요청 시 body로 인증번호 반환하도록 작성하였음
    @PostMapping("/email/{email}")
    public String sendJoinMail(@PathVariable("email") String email) {
        log.info("email : " + email);
        EmailMessage emailMessage = EmailMessage.builder()
                .to(email)
                .subject("[건강을부탁해] 이메일 인증을 위한 인증 코드 발송")
                .build();

        String code = emailService.sendMail(emailMessage, "email");
        log.info("code : " + code);
//        EmailResponseDto emailResponseDto = new EmailResponseDto();
//        emailResponseDto.setCode(code);
//
//        return ResponseEntity.ok(emailResponseDto);
        return code;

    }
}