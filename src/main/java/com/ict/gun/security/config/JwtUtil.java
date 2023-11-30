package com.ict.gun.security.config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expired-time}")
    private String expirationTime;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public String generate(String username, String type) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("role", type);
        return doGenerateToken(claims, username, type);
    }

    private String doGenerateToken(Map<String, Object> claims, String userId, String type) {
        long expirationTimeLong;
        if ("ACCESS".equals(type)) {
            expirationTimeLong = Long.parseLong(expirationTime) * 1000;
        } else {
            expirationTimeLong = Long.parseLong(expirationTime) * 1000 * 5;
        }
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("username").toString();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userId = getUsername(token);
        return (userId.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

}