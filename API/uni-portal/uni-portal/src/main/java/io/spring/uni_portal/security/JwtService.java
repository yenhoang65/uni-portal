package io.spring.uni_portal.security;

import io.jsonwebtoken.*;
import io.spring.uni_portal.model.User;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "super_secret_key_uni_portal_2025";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    private final byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
    private final SecretKeySpec key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getUserId())) // sử dụng userId
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long extractUserId(String token) {
        String subject = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
        return Long.parseLong(subject);
    }

    public boolean isTokenValid(String token, User user) {
        final Long userId = extractUserId(token);
        return userId.equals(user.getUserId()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}
