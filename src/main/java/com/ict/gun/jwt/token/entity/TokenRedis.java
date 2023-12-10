package com.ict.gun.jwt.token.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash(value="token")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TokenRedis {
    @Id
    private Long id;
    private String memEmail;
    private String token;
    private String refreshToken;
    private long exp;

    public TokenRedis(String memEmail, String token, String refreshToken, long exp) {
        this.memEmail = memEmail;
        this.token = token;
        this.refreshToken = refreshToken;
        this.exp = exp;
    }
}
