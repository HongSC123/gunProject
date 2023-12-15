package com.ict.gun.member.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class MemberUtil {
    public static String emailToFolderName(String email) {
        return email.substring(0, email.indexOf("@"));
    }

    public static Claims decodeToken(String token, String key) {
        return Jwts.parser()
                .setSigningKey(key)  // 여기서 key는 시크릿 키
                .parseClaimsJws(token)
                .getBody();
    }
}
