package com.ict.gun.chat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime; // 수정된 import

@Data
@Table(name = "\"CHAT\"")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chat {
    @Email
    @Column(name ="\"MEM_EMAIL\"")
    private String memEmail;

    @Column(name ="\"CHAT_FIX\"")
    private String chat_fix;

    @Column(name ="\"CHAT_CONTENT\"")
    private String chat_content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // Timestamp 형식에 맞게 변경
    @Column(name ="\"CHAT_DATE\"")
    private LocalDateTime chat_date; // LocalDateTime 타입으로 변경

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_seq")
    @SequenceGenerator(name = "chat_seq", sequenceName = "chat_seq", allocationSize = 1)
    @Column(name ="\"CHAT_NUM\"")
    private int chat_num;
}
