package com.ict.gun.common.email.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.email.dto.EmailPostDto;
import server.email.dto.EmailResponseDto;
import server.email.entity.EmailMessage;
import server.email.service.EmailService;

@RequestMapping("/send-mail")
@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;


    // 임시 비밀번호 발급
    @PostMapping("/password")
    public ResponseEntity sendPasswordMail(@RequestBody EmailPostDto emailPostDto) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(emailPostDto.getEmail())
                .subject("[SAVIEW] 임시 비밀번호 발급")
                .build();

        emailService.sendMail(emailMessage, "password");

        return ResponseEntity.ok().build();
    }

    // 회원가입 이메일 인증 - 요청 시 body로 인증번호 반환하도록 작성하였음
    @PostMapping("/email")
    public ResponseEntity sendJoinMail(@RequestBody EmailPostDto emailPostDto) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(emailPostDto.getEmail())
                .subject("[SAVIEW] 이메일 인증을 위한 인증 코드 발송")
                .build();

        String code = emailService.sendMail(emailMessage, "email");

        EmailResponseDto emailResponseDto = new EmailResponseDto();
        emailResponseDto.setCode(code);

        return ResponseEntity.ok(emailResponseDto);
    }
}