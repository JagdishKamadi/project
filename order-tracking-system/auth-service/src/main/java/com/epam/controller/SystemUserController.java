package com.epam.controller;

import com.epam.model.SystemUser;
import com.epam.model.dto.SystemUserDTO;
import com.epam.repository.SystemUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * REST controller for retrieving user information.
 * Endpoints are protected with role-based access control.
 */
@RestController
@RequestMapping("/order-tracking-system/v1/users/information")
public class SystemUserController {

    private final SystemUserRepository systemUserRepository;

    public SystemUserController(SystemUserRepository systemUserRepository) {
        this.systemUserRepository = systemUserRepository;
    }

    /**
     * Returns the profile of the currently authenticated user.
     * Accessible by any authenticated user (USER or ADMIN).
     */
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SystemUserDTO> getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = Objects.requireNonNull(authentication).getName();

        SystemUser user = systemUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found for email: " + email));

        return ResponseEntity.ok(toDto(user));
    }

    /**
     * Returns all registered users. Restricted to ADMIN role only.
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SystemUserDTO>> getAllUserDetails() {
        List<SystemUserDTO> users = systemUserRepository.findAll().stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(users);
    }

    private SystemUserDTO toDto(SystemUser entity) {
        return SystemUserDTO.builder()
                .id(entity.getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .email(entity.getEmail())
                .role(entity.getRole())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
