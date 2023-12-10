package com.ict.gun.jwt.token.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash(value="token")
// @AllArgsConstructor
@NoArgsConstructor
@ToString
public class TokenRedis {
    @Id
    private String id;
    // private String memEmail;
    private String token;
    private String refreshToken;
    private long exp;
    private long refreshExp;

    public TokenRedis(String memEmail, String token, String refreshToken, long exp, long refreshExp) {
        this.id = memEmail;
        this.token = token;
        this.refreshToken = refreshToken;
        this.exp = exp;
        this.refreshExp = refreshExp;
    }
}
