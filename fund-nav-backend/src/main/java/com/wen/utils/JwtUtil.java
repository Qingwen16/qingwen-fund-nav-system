package com.wen.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private static final SecretKey KEY = Keys.hmacShaKeyFor(
            "your_secret_key_must_be_at_least_32_bytes!!".getBytes()
    );
    private static final long EXPIRE_MS = 7 * 24 * 60 * 60 * 1000L;

    /**
     * 生成 token
     */
    public String generateToken(Long userId, String openid) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("openid", openid)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_MS))
                .signWith(KEY)
                .compact();
    }

    /**
     * 解析 token
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 获取 userId
     */
    public Long getUserId(String token) {
        return parseToken(token).get("userId", Long.class);
    }

    /**
     * 获取 openid
     */
    public String getOpenid(String token) {
        return parseToken(token).get("openid", String.class);
    }

    /**
     * 获取 token 剩余有效时间（毫秒）
     */
    public long getExpireTime(String token) {
        Date expiration = parseToken(token).getExpiration();
        return Math.max(expiration.getTime() - System.currentTimeMillis(), 0);
    }
}