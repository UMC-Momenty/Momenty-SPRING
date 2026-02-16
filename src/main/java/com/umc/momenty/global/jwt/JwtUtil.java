package com.umc.momenty.global.jwt;

import com.umc.momenty.global.oauth.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long accessTokenValiditySeconds;
    private final long refreshTokenValiditySeconds;

    public JwtUtil(
            @Value("${spring.jwt.secret-key}") String secretKey,
            @Value("${spring.jwt.access-token-expiration}") long accessTokenValiditySeconds,
            @Value("${spring.jwt.refresh-token-expiration}") long refreshTokenValiditySeconds

    ){
        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public TokenDto generateTokens(Long userId, String providerId, String role) {
        String accessToken = createToken(userId, providerId,role, "access", accessTokenValiditySeconds);
        String refreshToken = createToken(userId, providerId, role, "refresh", refreshTokenValiditySeconds);
        return TokenDto.of(accessToken, refreshToken);
    }

    public String createToken(Long userId, String providerId, String role, String tokenType, long expiredTime ) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("providerId", providerId)
                .claim("role", role)
                .claim("tokenType", tokenType)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredTime * 1000))
                .signWith(secretKey)
                .compact();
    }

    public Date getExpiration(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean isTokenExpired(String token) {
        if (token == null || token.isBlank()) {
            return true;
        }
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration()
                    .before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return true;
        }
    }

    public Long getRemainingTime(String token) {
        Date expiration = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        long now = System.currentTimeMillis();
        return (expiration.getTime() - now) / 1000;
    }
}
