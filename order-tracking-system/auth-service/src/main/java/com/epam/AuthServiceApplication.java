package com.epam;

import com.epam.model.JwtProperties;
import com.epam.model.Role;
import com.epam.model.SystemUser;
import com.epam.repository.SystemUserRepository;
import com.epam.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Entry point for the Authentication Service application.
 * Enables JWT configuration properties binding.
 */
@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class AuthServiceApplication implements CommandLineRunner {

    private final SystemUserRepository systemUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceApplication(SystemUserRepository systemUserRepository, PasswordEncoder passwordEncoder) {
        this.systemUserRepository = systemUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        SystemUser admin = SystemUser.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password(passwordEncoder.encode("password123"))
                .role(Role.ROLE_ADMIN)
                .build();

        SystemUser systemUser = SystemUser.builder()
                .firstname("Peter")
                .lastname("Parker")
                .email("peter.parker@example.com")
                .password(passwordEncoder.encode("parker1234"))
                .role(Role.ROLE_USER)
                .build();

        systemUserRepository.save(admin);
        systemUserRepository.save(systemUser);
    }
}
