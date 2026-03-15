package com.epam.service;

import com.epam.model.JwtProperties;
import com.epam.model.SystemUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

/**
 * Service responsible for generating JWT tokens for authenticated users.
 */
@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * Generates a signed JWT token containing the user's email as subject.
     *
     * @param systemUser the authenticated user
     * @return signed JWT token string
     */
    public String generateToken(SystemUser systemUser) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(systemUser.getEmail())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(jwtProperties.getExpiration())))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }
}
