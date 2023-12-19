package com.ict.gun.email.controller;

import com.ict.gun.email.dto.EmailPostDto;
import com.ict.gun.email.entity.EmailMessage;
import com.ict.gun.email.service.EmailService;
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

    @PostMapping("/password")
    public ResponseEntity sendPasswordMail(@RequestBody EmailPostDto emailPostDto) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(emailPostDto.getEmail())
                .subject("[건강을부탁해] 임시 비밀번호 발급")
                .build();
        emailService.sendMail(emailMessage, "password");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/email/{email}")
    public String sendJoinMail(@PathVariable("email") String email) {
        log.info("email : " + email);
        EmailMessage emailMessage = EmailMessage.builder()
                .to(email)
                .subject("[건강을부탁해] 이메일 인증을 위한 인증 코드 발송")
                .build();

        String code = emailService.sendMail(emailMessage, "email");
        log.info("code : " + code);
        return code;

    }
}