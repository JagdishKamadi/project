package com.epam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-tracking-system/v1/users/")
public class UserService {

    @GetMapping("/greetings")
    public ResponseEntity<String> greetings() {
        return ResponseEntity.ok("Hello World!");
    }
}
