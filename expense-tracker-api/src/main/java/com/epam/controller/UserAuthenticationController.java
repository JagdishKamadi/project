package com.epam.controller;


import com.epam.dto.LoginUserDto;
import com.epam.dto.RegisterUserDto;
import com.epam.model.AppUser;
import com.epam.model.LoginResponse;
import com.epam.securityservice.JWTTokenManagerService;
import com.epam.securityservice.UserAuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/users")
public class UserAuthenticationController {

    private final JWTTokenManagerService jwtTokenManagerService;
    private final UserAuthenticationService userAuthenticationService;

    public UserAuthenticationController(JWTTokenManagerService jwtTokenManagerService, UserAuthenticationService userAuthenticationService) {
        this.jwtTokenManagerService = jwtTokenManagerService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<AppUser> signUp(@RequestBody RegisterUserDto registerUserDto) {
        return ResponseEntity.ok(userAuthenticationService.register(registerUserDto));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        AppUser appUser = userAuthenticationService.login(loginUserDto);
        String token = jwtTokenManagerService.generateToken(appUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .expiresIn(getTokenExpiryTime())
                .build();

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping(value = "/me")
    public ResponseEntity<AppUser> getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) authentication.getPrincipal();
        return ResponseEntity.ok(appUser);
    }

    private String getTokenExpiryTime() {
        long expiration = jwtTokenManagerService.getExpirationTime();

        // Get current date and time
        Date date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MILLISECOND, (int) expiration);

        // Convert the updated Date to an Instant
        Instant instant = calendar.getTime().toInstant();

        // Convert the Instant to ZonedDateTime (assuming default system time zone)
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

        // Format to a more readable form
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy");
        return zonedDateTime.format(formatter);
    }
}
