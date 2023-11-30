package com.ict.gun.security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ict.gun.member.dto.RequestLoginDto;
import com.ict.gun.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody RequestLoginDto param) throws JsonProcessingException {
        return authService.login(param);
    }
}
