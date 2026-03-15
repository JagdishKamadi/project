package com.epam.exception;

/**
 * Thrown when a user registration attempt uses an email that is already registered.
 */
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
