package com.sensormonitor.backend.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long expirationTime;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expirationTime) {

        this.signingKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

        this.expirationTime = expirationTime;
    }

    public String generateToken(String email) {

        Date issuedAt = new Date();

        Date expiration = new Date(
                issuedAt.getTime() + expirationTime
        );

        return Jwts.builder()
                .subject(email)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(signingKey)
                .compact();
    }

    public String extractEmail(String token) {

        return getClaims(token)
                .getSubject();
    }

    public boolean isTokenValid(String token) {

        try {

            Claims claims = getClaims(token);

            return claims.getExpiration()
                    .after(new Date());

        } catch (Exception exception) {

            return false;
        }
    }

    private Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}