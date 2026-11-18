package com.epam.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Integer id;

    @NotBlank(message = "firstName should not be blank")
    private String firstName;

    @NotBlank(message = "lastName should not be blank")
    private String lastName;

    @NotBlank(message = "email should not be blank")
    @Email(message = "email should be a valid email address")
    private String email;

    @NotNull(message = "age should not be null")
    @Min(value = 18, message = "age should be at least 18")
    private Integer age;

    @NotNull(message = "address should not be null")
    @Valid
    private AddressDTO address;
}
