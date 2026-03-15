package com.epam.controller;

import com.epam.model.TokenResponse;
import com.epam.model.dto.SystemUserDTO;
import com.epam.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller handling user authentication operations.
 * Provides endpoints for user registration and login.
 */
@RestController
@RequestMapping("/order-tracking-system/v1/users")
@Validated
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SystemUserDTO> signUp(@Valid @RequestBody SystemUserDTO systemUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(systemUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody SystemUserDTO systemUserDto) {
        return ResponseEntity.ok(authService.login(systemUserDto));
    }
}
