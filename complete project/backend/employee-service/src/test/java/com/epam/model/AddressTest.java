package com.epam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddressTest {

    private static final Long VALID_ID = 1L;
    private static final String VALID_HOUSE_NUMBER = "12A";
    private static final String VALID_BUILDING_NAME = "Sunrise Apartments";
    private static final String VALID_STREET = "MG Road";
    private static final String VALID_LANDMARK = "Near City Mall";
    private static final String VALID_LOCALITY = "Koregaon Park";
    private static final String VALID_VILLAGE = "Wagholi";
    private static final String VALID_CITY = "Pune";
    private static final String VALID_DISTRICT = "Pune";
    private static final String VALID_STATE = "Maharashtra";
    private static final String VALID_COUNTRY = "India";
    private static final String VALID_PINCODE = "411001";
    private static final String VALID_ADDRESS_TYPE = "HOME";
    private static final LocalDateTime VALID_CREATED_AT = LocalDateTime.of(2026, 1, 1, 10, 0);
    private static final LocalDateTime VALID_UPDATED_AT = LocalDateTime.of(2026, 1, 2, 11, 0);

    private Address address;

    @BeforeEach
    void setUp() {
        address = buildValidAddress();
    }

    private Address buildValidAddress() {
        return Address.builder()
                .id(VALID_ID)
                .houseNumber(VALID_HOUSE_NUMBER)
                .buildingName(VALID_BUILDING_NAME)
                .street(VALID_STREET)
                .landmark(VALID_LANDMARK)
                .locality(VALID_LOCALITY)
                .village(VALID_VILLAGE)
                .city(VALID_CITY)
                .district(VALID_DISTRICT)
                .state(VALID_STATE)
                .country(VALID_COUNTRY)
                .pincode(VALID_PINCODE)
                .addressType(VALID_ADDRESS_TYPE)
                .createdAt(VALID_CREATED_AT)
                .updatedAt(VALID_UPDATED_AT)
                .build();
    }

    @Test
    void testBuilderWhenAllFieldsAreProvided() {
        assertAll(() -> assertEquals(VALID_ID, address.getId()),
                () -> assertEquals(VALID_HOUSE_NUMBER, address.getHouseNumber()),
                () -> assertEquals(VALID_BUILDING_NAME, address.getBuildingName()),
                () -> assertEquals(VALID_STREET, address.getStreet()),
                () -> assertEquals(VALID_LANDMARK, address.getLandmark()),
                () -> assertEquals(VALID_LOCALITY, address.getLocality()),
                () -> assertEquals(VALID_VILLAGE, address.getVillage()),
                () -> assertEquals(VALID_CITY, address.getCity()),
                () -> assertEquals(VALID_DISTRICT, address.getDistrict()),
                () -> assertEquals(VALID_STATE, address.getState()),
                () -> assertEquals(VALID_COUNTRY, address.getCountry()),
                () -> assertEquals(VALID_PINCODE, address.getPincode()),
                () -> assertEquals(VALID_ADDRESS_TYPE, address.getAddressType()),
                () -> assertEquals(VALID_CREATED_AT, address.getCreatedAt()),
                () -> assertEquals(VALID_UPDATED_AT, address.getUpdatedAt()));
    }

    @Test
    void testNoArgsConstructorWhenInvoked() {
        Address emptyAddress = new Address();

        assertAll(() -> assertNull(emptyAddress.getId()),
                () -> assertNull(emptyAddress.getHouseNumber()),
                () -> assertNull(emptyAddress.getBuildingName()),
                () -> assertNull(emptyAddress.getStreet()),
                () -> assertNull(emptyAddress.getLandmark()),
                () -> assertNull(emptyAddress.getLocality()),
                () -> assertNull(emptyAddress.getVillage()),
                () -> assertNull(emptyAddress.getCity()),
                () -> assertNull(emptyAddress.getDistrict()),
                () -> assertNull(emptyAddress.getState()),
                () -> assertNull(emptyAddress.getCountry()),
                () -> assertNull(emptyAddress.getPincode()),
                () -> assertNull(emptyAddress.getAddressType()),
                () -> assertNull(emptyAddress.getCreatedAt()),
                () -> assertNull(emptyAddress.getUpdatedAt()));
    }

    @Test
    void testAllArgsConstructorWhenAllFieldsProvided() {
        Address allArgsAddress = new Address(VALID_ID, VALID_HOUSE_NUMBER, VALID_BUILDING_NAME, VALID_STREET,
                VALID_LANDMARK, VALID_LOCALITY, VALID_VILLAGE, VALID_CITY, VALID_DISTRICT, VALID_STATE,
                VALID_COUNTRY, VALID_PINCODE, VALID_ADDRESS_TYPE, VALID_CREATED_AT, VALID_UPDATED_AT);

        assertAll(() -> assertEquals(VALID_ID, allArgsAddress.getId()),
                () -> assertEquals(VALID_HOUSE_NUMBER, allArgsAddress.getHouseNumber()),
                () -> assertEquals(VALID_STREET, allArgsAddress.getStreet()),
                () -> assertEquals(VALID_CITY, allArgsAddress.getCity()),
                () -> assertEquals(VALID_STATE, allArgsAddress.getState()),
                () -> assertEquals(VALID_COUNTRY, allArgsAddress.getCountry()),
                () -> assertEquals(VALID_PINCODE, allArgsAddress.getPincode()),
                () -> assertEquals(VALID_ADDRESS_TYPE, allArgsAddress.getAddressType()),
                () -> assertEquals(VALID_CREATED_AT, allArgsAddress.getCreatedAt()),
                () -> assertEquals(VALID_UPDATED_AT, allArgsAddress.getUpdatedAt()));
    }

    @Test
    void testSettersAndGettersWhenValuesAreChanged() {
        LocalDateTime newCreatedAt = LocalDateTime.of(2026, 2, 1, 9, 0);
        LocalDateTime newUpdatedAt = LocalDateTime.of(2026, 2, 2, 10, 0);

        address.setId(2L);
        address.setHouseNumber("45B");
        address.setBuildingName("Green Valley");
        address.setStreet("FC Road");
        address.setLandmark("Near Park");
        address.setLocality("Deccan");
        address.setVillage("Shivane");
        address.setCity("Mumbai");
        address.setDistrict("Mumbai");
        address.setState("Maharashtra");
        address.setCountry("India");
        address.setPincode("400001");
        address.setAddressType("OFFICE");
        address.setCreatedAt(newCreatedAt);
        address.setUpdatedAt(newUpdatedAt);

        assertAll(() -> assertEquals(2L, address.getId()),
                () -> assertEquals("45B", address.getHouseNumber()),
                () -> assertEquals("Green Valley", address.getBuildingName()),
                () -> assertEquals("FC Road", address.getStreet()),
                () -> assertEquals("Near Park", address.getLandmark()),
                () -> assertEquals("Deccan", address.getLocality()),
                () -> assertEquals("Shivane", address.getVillage()),
                () -> assertEquals("Mumbai", address.getCity()),
                () -> assertEquals("Mumbai", address.getDistrict()),
                () -> assertEquals("Maharashtra", address.getState()),
                () -> assertEquals("India", address.getCountry()),
                () -> assertEquals("400001", address.getPincode()),
                () -> assertEquals("OFFICE", address.getAddressType()),
                () -> assertEquals(newCreatedAt, address.getCreatedAt()),
                () -> assertEquals(newUpdatedAt, address.getUpdatedAt()));
    }

    @Test
    void testEqualsWhenComparedWithSameReference() {
        Object sameReference = address;

        assertEquals(address, sameReference);
    }

    @Test
    void testEqualsWhenComparedWithEqualObject() {
        Address other = buildValidAddress();

        assertEquals(address, other);
        assertEquals(address.hashCode(), other.hashCode());
    }

    @Test
    void testEqualsWhenComparedWithDifferentValues() {
        Address other = buildValidAddress();
        other.setCity("Mumbai");

        assertNotEquals(address, other);
    }

    @Test
    void testEqualsWhenComparedWithNull() {
        assertNotEquals(null, address);
    }

    @Test
    void testEqualsWhenComparedWithDifferentClass() {
        assertNotEquals(new Object(), address);
    }

    @Test
    void testToStringWhenInvoked() {
        String result = address.toString();

        assertTrue(result.contains(VALID_CITY));
    }
}