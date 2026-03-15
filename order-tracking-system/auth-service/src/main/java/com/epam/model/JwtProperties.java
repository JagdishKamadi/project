package com.epam.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for JWT token generation.
 * Bound from the {@code jwt} prefix in application configuration.
 */
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {

    /** Secret key used to sign JWT tokens. */
    private String secret;

    /** Token expiration time in milliseconds. */
    private long expiration;
}
