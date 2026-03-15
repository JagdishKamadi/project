package com.epam.exception;

import lombok.Builder;

import java.time.Instant;

/**
 * Standardized error response structure returned by all exception handlers.
 *
 * @param timestamp time when the error occurred
 * @param message   human-readable error description
 * @param details   additional context about the error
 */
@Builder
public record ErrorResponse(
        Instant timestamp,
        String message,
        String details
) {
}
