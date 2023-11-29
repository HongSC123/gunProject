package com.ict.gun.email.entity;

import lombok.Data;
import lombok.Builder;
@Data
@Builder
public class EmailMessage {
    private String to;      // 수신자
    private String subject; // 메일 제목
    private String message; // 메일 내용
}