package com.epam.service;

import com.epam.exception.UserAlreadyExistsException;
import com.epam.model.SystemUser;
import com.epam.model.TokenResponse;
import com.epam.model.dto.SystemUserDTO;
import com.epam.repository.SystemUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service handling user registration and authentication logic.
 */
@Slf4j
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SystemUserRepository systemUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       SystemUserRepository systemUserRepository,
                       PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.systemUserRepository = systemUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user after verifying email uniqueness and encoding the password.
     *
     * @param systemUserDto registration data
     * @return the created user as a DTO (password excluded)
     * @throws UserAlreadyExistsException if the email is already registered
     */
    @Transactional
    public SystemUserDTO signUp(SystemUserDTO systemUserDto) {
        if (systemUserRepository.findByEmail(systemUserDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(
                    "A user with email '" + systemUserDto.getEmail() + "' already exists");
        }

        SystemUser systemUser = toEntity(systemUserDto);
        systemUser.setPassword(passwordEncoder.encode(systemUserDto.getPassword()));

        SystemUser savedUser = systemUserRepository.save(systemUser);
        log.info("User registered successfully with email: {}", savedUser.getEmail());

        return toDto(savedUser);
    }

    /**
     * Authenticates a user and issues a JWT token.
     *
     * @param systemUserDto login credentials
     * @return token response containing the JWT
     */
    @Transactional(readOnly = true)
    public TokenResponse login(SystemUserDTO systemUserDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        systemUserDto.getEmail(), systemUserDto.getPassword())
        );

        if (!(authentication.getPrincipal() instanceof SystemUser principal)) {
            throw new IllegalStateException("Unexpected principal type after authentication");
        }
        String token = jwtService.generateToken(principal);
        log.info("User authenticated successfully: {}", principal.getEmail());

        return new TokenResponse(principal.getUsername(), token);
    }

    private SystemUser toEntity(SystemUserDTO dto) {
        return SystemUser.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    private SystemUserDTO toDto(SystemUser entity) {
        return SystemUserDTO.builder()
                .id(entity.getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .email(entity.getEmail())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
