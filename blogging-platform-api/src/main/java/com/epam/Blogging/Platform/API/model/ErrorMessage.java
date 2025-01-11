package com.epam.Blogging.Platform.API.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ErrorMessage {
    private String path;
    private HttpStatus status;
    private String message;
    private LocalDateTime localDateTime;
}
