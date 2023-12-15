package com.ict.gun.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

    private String memEmail;
    private String memPw;
}
