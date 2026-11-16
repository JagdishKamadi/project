package com.epam.dto;

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
    private String houseNumber;
    private String buildingName;
    private String street;
    private String landmark;
    private String locality;
    private String village;
    private String city;
    private String district;
    private String state;
    private String country;
    private String pincode;
    private String addressType; // HOME, OFFICE, BILLING, SHIPPING
}
