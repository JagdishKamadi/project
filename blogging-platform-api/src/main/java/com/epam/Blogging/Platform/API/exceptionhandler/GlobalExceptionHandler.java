package com.epam.Blogging.Platform.API.exceptionhandler;

import com.epam.Blogging.Platform.API.exception.BlogNotFoundException;
import com.epam.Blogging.Platform.API.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BlogNotFoundException.class)
    public ResponseEntity<ErrorMessage> blogIdNotFoundExceptionHandler(WebRequest webRequest, BlogNotFoundException blogNotFoundException) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .path(webRequest.getDescription(false))
                .message(blogNotFoundException.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .localDateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
