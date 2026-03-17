package com.epam.model.dto;

import com.epam.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

/**
 * Data Transfer Object for user registration and authentication requests.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemUserDTO {

    private Integer id;
    private String firstname;
    private String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    /** User role; returned in responses but not required during registration. */
    private Role role;

    private Instant createdAt;
    private Instant updatedAt;
}
