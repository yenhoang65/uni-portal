package io.spring.uni_portal.security;

import io.jsonwebtoken.*;
import io.spring.uni_portal.model.User;
import jakarta.servlet.http.HttpServletRequest;
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
                .setSubject(String.valueOf(user.getUserId()))
                .claim("role", user.getRole())
                .claim("user_id", user.getUserId())
                .claim("userName", user.getUserName()) // Ví dụ thêm thông tin khác
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public Long getUserIdFromToken(HttpServletRequest request) {
        String token = extractToken(request);
        if (token != null) {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody();
            return Long.parseLong(claims.getSubject());
        }
        return null;
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Loại bỏ "Bearer " và trả về phần còn lại của token
        }
        return null;
    }


    public Long extractUserId(String token) {
        try {
            String subject = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody().getSubject();
            return Long.parseLong(subject);
        } catch (JwtException | NumberFormatException e) {
            return null;
        }
    }

    public boolean isTokenValid(String token, User user) {
        final Long userId = extractUserId(token);
        String roleFromToken = extractRoleFromToken(token); // Phương thức mới để lấy role từ token
        return userId != null && userId.equals(user.getUserId()) && roleFromToken.equals(user.getRole()) && !isTokenExpired(token);
    }

    private String extractRoleFromToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody().get("role", String.class);
        } catch (JwtException e) {
            return null;
        }
    }


    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}
