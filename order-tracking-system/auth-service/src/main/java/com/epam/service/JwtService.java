package com.epam.service;

import com.epam.model.JwtProperties;
import com.epam.model.SystemUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

/**
 * Service responsible for generating and parsing JWT tokens.
 * Embeds user identity and role information as token claims.
 */
@Service
public class JwtService {

    private static final String ROLE_CLAIM = "role";

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * Generates a signed JWT token containing the user's email as subject
     * and their role as a custom claim.
     *
     * @param systemUser the authenticated user
     * @return signed JWT token string
     */
    public String generateToken(SystemUser systemUser) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(systemUser.getEmail())
                .claim(ROLE_CLAIM, systemUser.getRole().name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(jwtProperties.getExpiration())))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extracts the username (email) from the given JWT token.
     *
     * @param token JWT token string
     * @return the subject (email) from the token
     */
    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * Extracts the role claim from the given JWT token.
     *
     * @param token JWT token string
     * @return the role string embedded in the token
     */
    public String getRoleFromToken(String token) {
        return parseClaims(token).get(ROLE_CLAIM, String.class);
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }
}
