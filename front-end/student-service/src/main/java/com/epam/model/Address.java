
package com.epam.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
