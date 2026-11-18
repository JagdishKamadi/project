package com.epam.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ErrorResponse handleEmployeeNotFoundException(WebRequest webRequest, EmployeeNotFoundException employeeNotFoundException) {
        log.warn("Employee not found - path: {}, message: {}",
                requestPath(webRequest), employeeNotFoundException.getMessage());

        return ErrorResponse.builder(employeeNotFoundException, HttpStatus.NOT_FOUND, employeeNotFoundException.getMessage())
                .title("Employee Not Found")
                .instance(URI.create(requestPath(webRequest)))
                .property("timestamp", Instant.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(WebRequest webRequest, MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            String message = fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Invalid value";
            fieldErrors.merge(fieldError.getField(), message, (existing, incoming) -> existing + "; " + incoming);
        }

        log.warn("Validation failed - path: {}, errors: {}", requestPath(webRequest), fieldErrors);

        return ErrorResponse.builder(methodArgumentNotValidException, HttpStatus.BAD_REQUEST, "Validation failed for one or more fields")
                .title("Invalid Request")
                .instance(URI.create(requestPath(webRequest)))
                .property("timestamp", Instant.now())
                .property("fieldErrors", fieldErrors)
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleUnexpectedException(WebRequest webRequest, Exception exception) {
        log.error("Unhandled exception occurred - path: {}", requestPath(webRequest), exception);

        return ErrorResponse.builder(exception, HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred")
                .title("Internal Server Error")
                .instance(URI.create(requestPath(webRequest)))
                .property("timestamp", Instant.now())
                .build();
    }

    private String requestPath(WebRequest webRequest) {
        return webRequest.getDescription(false).replace("uri=", "");
    }
}

