package com.epam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long id;

    @NotBlank(message = "houseNumber should not be blank")
    private String houseNumber;

    private String buildingName;

    @NotBlank(message = "street should not be blank")
    private String street;

    private String landmark;

    private String locality;

    private String village;

    @NotBlank(message = "city should not be blank")
    private String city;

    private String district;

    @NotBlank(message = "state should not be blank")
    private String state;

    @NotBlank(message = "country should not be blank")
    private String country;

    @NotBlank(message = "pincode should not be blank")
    @Pattern(regexp = "^[0-9]{6}$", message = "pincode should be a valid 6-digit number")
    private String pincode;

    @NotBlank(message = "addressType should not be blank")
    @Pattern(regexp = "^(HOME|OFFICE|BILLING|SHIPPING)$", message = "addressType should be one of HOME, OFFICE, BILLING, SHIPPING")
    private String addressType; // HOME, OFFICE, BILLING, SHIPPING
}
