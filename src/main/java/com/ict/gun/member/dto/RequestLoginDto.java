package com.ict.gun.member.dto;

import lombok.Data;

@Data
public class RequestLoginDto {
    private String memEmail;
    private String memPw;
}
