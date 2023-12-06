package com.ict.gun.jwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "token")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_generator")
    @SequenceGenerator(name = "token_generator", sequenceName = "tokens_seq", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TOKEN")
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "TOKEN_TYPE")
    private TokenType tokenType;

    @Column(name = "EXPIRED")
    private boolean expired;

    @Column(name = "REVOKED")
    private boolean revoked;

    @Column(name = "USERID")
    private String userId;
}
