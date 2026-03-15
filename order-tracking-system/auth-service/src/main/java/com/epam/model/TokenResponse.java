package com.epam.model;

/**
 * Immutable response containing the authenticated user's email and JWT token.
 *
 * @param username authenticated user's email
 * @param token    JWT access token
 */
public record TokenResponse(String username, String token) {
}
