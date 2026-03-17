package com.epam.model;

/**
 * Enumeration of application-level user roles.
 * Prefixed with {@code ROLE_} to align with Spring Security's default authority convention.
 */
public enum Role {
    ROLE_USER,
    ROLE_ADMIN
}

