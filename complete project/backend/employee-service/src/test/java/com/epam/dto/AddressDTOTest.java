package com.epam.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddressDTOTest {

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
    private static final String INVALID_PINCODE_FORMAT = "ABCDEF";
    private static final String INVALID_ADDRESS_TYPE = "UNKNOWN";

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    private AddressDTO addressDTO;

    @BeforeAll
    static void initValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidator() {
        validatorFactory.close();
    }

    @BeforeEach
    void setUp() {
        addressDTO = buildValidAddress();
    }

    private AddressDTO buildValidAddress() {
        return AddressDTO.builder()
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
                .build();
    }

    @Test
    void testBuilderWhenAllFieldsAreProvided() {
        assertAll(() -> assertEquals(VALID_ID, addressDTO.getId()),
                () -> assertEquals(VALID_HOUSE_NUMBER, addressDTO.getHouseNumber()),
                () -> assertEquals(VALID_BUILDING_NAME, addressDTO.getBuildingName()),
                () -> assertEquals(VALID_STREET, addressDTO.getStreet()),
                () -> assertEquals(VALID_LANDMARK, addressDTO.getLandmark()),
                () -> assertEquals(VALID_LOCALITY, addressDTO.getLocality()),
                () -> assertEquals(VALID_VILLAGE, addressDTO.getVillage()),
                () -> assertEquals(VALID_CITY, addressDTO.getCity()),
                () -> assertEquals(VALID_DISTRICT, addressDTO.getDistrict()),
                () -> assertEquals(VALID_STATE, addressDTO.getState()),
                () -> assertEquals(VALID_COUNTRY, addressDTO.getCountry()),
                () -> assertEquals(VALID_PINCODE, addressDTO.getPincode()),
                () -> assertEquals(VALID_ADDRESS_TYPE, addressDTO.getAddressType()));
    }

    @Test
    void testNoArgsConstructorWhenInvoked() {
        AddressDTO emptyAddressDTO = new AddressDTO();

        assertAll(() -> assertNull(emptyAddressDTO.getId()),
                () -> assertNull(emptyAddressDTO.getHouseNumber()),
                () -> assertNull(emptyAddressDTO.getBuildingName()),
                () -> assertNull(emptyAddressDTO.getStreet()),
                () -> assertNull(emptyAddressDTO.getLandmark()),
                () -> assertNull(emptyAddressDTO.getLocality()),
                () -> assertNull(emptyAddressDTO.getVillage()),
                () -> assertNull(emptyAddressDTO.getCity()),
                () -> assertNull(emptyAddressDTO.getDistrict()),
                () -> assertNull(emptyAddressDTO.getState()),
                () -> assertNull(emptyAddressDTO.getCountry()),
                () -> assertNull(emptyAddressDTO.getPincode()),
                () -> assertNull(emptyAddressDTO.getAddressType()));
    }

    @Test
    void testAllArgsConstructorWhenAllFieldsProvided() {
        AddressDTO address = new AddressDTO(VALID_ID, VALID_HOUSE_NUMBER, VALID_BUILDING_NAME, VALID_STREET,
                VALID_LANDMARK, VALID_LOCALITY, VALID_VILLAGE, VALID_CITY, VALID_DISTRICT, VALID_STATE,
                VALID_COUNTRY, VALID_PINCODE, VALID_ADDRESS_TYPE);

        assertAll(() -> assertEquals(VALID_ID, address.getId()),
                () -> assertEquals(VALID_HOUSE_NUMBER, address.getHouseNumber()),
                () -> assertEquals(VALID_STREET, address.getStreet()),
                () -> assertEquals(VALID_CITY, address.getCity()),
                () -> assertEquals(VALID_STATE, address.getState()),
                () -> assertEquals(VALID_COUNTRY, address.getCountry()),
                () -> assertEquals(VALID_PINCODE, address.getPincode()),
                () -> assertEquals(VALID_ADDRESS_TYPE, address.getAddressType()));
    }

    @Test
    void testSettersAndGettersWhenValuesAreChanged() {
        addressDTO.setId(2L);
        addressDTO.setHouseNumber("45B");
        addressDTO.setBuildingName("Green Valley");
        addressDTO.setStreet("FC Road");
        addressDTO.setLandmark("Near Park");
        addressDTO.setLocality("Deccan");
        addressDTO.setVillage("Shivane");
        addressDTO.setCity("Mumbai");
        addressDTO.setDistrict("Mumbai");
        addressDTO.setState("Maharashtra");
        addressDTO.setCountry("India");
        addressDTO.setPincode("400001");
        addressDTO.setAddressType("OFFICE");

        assertAll(() -> assertEquals(2L, addressDTO.getId()),
                () -> assertEquals("45B", addressDTO.getHouseNumber()),
                () -> assertEquals("Green Valley", addressDTO.getBuildingName()),
                () -> assertEquals("FC Road", addressDTO.getStreet()),
                () -> assertEquals("Near Park", addressDTO.getLandmark()),
                () -> assertEquals("Deccan", addressDTO.getLocality()),
                () -> assertEquals("Shivane", addressDTO.getVillage()),
                () -> assertEquals("Mumbai", addressDTO.getCity()),
                () -> assertEquals("Mumbai", addressDTO.getDistrict()),
                () -> assertEquals("Maharashtra", addressDTO.getState()),
                () -> assertEquals("India", addressDTO.getCountry()),
                () -> assertEquals("400001", addressDTO.getPincode()),
                () -> assertEquals("OFFICE", addressDTO.getAddressType()));
    }

    @Test
    void testEqualsWhenComparedWithSameReference() {
        Object sameReference = addressDTO;

        assertEquals(addressDTO, sameReference);
    }

    @Test
    void testEqualsWhenComparedWithEqualObject() {
        AddressDTO other = buildValidAddress();

        assertEquals(addressDTO, other);
        assertEquals(addressDTO.hashCode(), other.hashCode());
    }

    @Test
    void testEqualsWhenComparedWithDifferentValues() {
        AddressDTO other = buildValidAddress();
        other.setCity("Mumbai");

        assertNotEquals(addressDTO, other);
    }

    @Test
    void testEqualsWhenComparedWithNull() {
        assertNotEquals(null, addressDTO);
    }

    @Test
    void testEqualsWhenComparedWithDifferentClass() {
        assertNotEquals(new Object(), addressDTO);
    }

    @Test
    void testToStringWhenInvoked() {
        String result = addressDTO.toString();

        assertTrue(result.contains(VALID_CITY));
    }

    @Test
    void testValidationWhenAllFieldsAreValid() {
        Set<ConstraintViolation<AddressDTO>> violations = validator.validate(addressDTO);

        assertTrue(violations.isEmpty(), "Expected no violations for a fully valid AddressDTO");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void testHouseNumberValidationWhenValueIsBlankOrNull(String invalidHouseNumber) {
        addressDTO.setHouseNumber(invalidHouseNumber);

        Set<ConstraintViolation<AddressDTO>> violations = validator.validateProperty(addressDTO, "houseNumber");

        assertFalse(violations.isEmpty(), "Expected violations for blank/null houseNumber");
    }

    @Test
    void testHouseNumberValidationWhenValueIsValid() {
        Set<ConstraintViolation<AddressDTO>> violations = validator.validateProperty(addressDTO, "houseNumber");

        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void testStreetValidationWhenValueIsBlankOrNull(String invalidStreet) {
        addressDTO.setStreet(invalidStreet);

        Set<ConstraintViolation<AddressDTO>> violations = validator.validateProperty(addressDTO, "street");

        assertFalse(violations.isEmpty(), "Expected violations for blank/null street");
    }

    @Test
    void testStreetValidationWhenValueIsValid() {
        Set<ConstraintViolation<AddressDTO>> violations = validator.validateProperty(addressDTO, "street");

        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void testCityValidationWhenValueIsBlankOrNull(String invalidCity) {
        addressDTO.setCity(invalidCity);

        Set<ConstraintViolation<AddressDTO>> violations = validator.validateProperty(addressDTO, "city");

        assertFalse(violations.isEmpty(), "Expected violations for blank/null city");
    }

    @Test
    void testCityValidationWhenValueIsValid() {
        Set<ConstraintViolation<AddressDTO>> violations = validator.validateProperty(addressDTO, "city");

        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void testStateValidationWhenValueIsBlankOrNull(String invalidState) {
        addressDTO.setState(invalidState);

        Set<ConstraintViolation<AddressDTO>> violations = validator.validateProperty(addressDTO, "state");

        assertFalse(violations.isEmpty(), "Expected violations for blank/null state");
    }

    @Test
    void testStateValidationWhenValueIsValid() {
        Set<ConstraintViolation<AddressDTO>> violations = validator.validateProperty(addressDTO, "state");

        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void testCountryValidationWhenValueIsBlankOrNull(String invalidCountry) {
        addressDTO.setCountry(invalidCountry);

        Set<ConstraintViolation<AddressDTO>> violations = validator.validateProperty(addressDTO, "country");

        assertFalse(violations.isEmpty(), "Expected violations for blank/null country");
    }

    @Test
    void testCountryValidationWhenValueIsValid() {
        Set<ConstraintViolation<AddressDTO>> violations = validator.validateProperty(addressDTO, "country");

        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   ", INVALID_PINCODE_FORMAT, "12345", "1234567"})
    void testPincodeValidationWhenValueIsInvalid(String invalidPincode) {
        addressDTO.setPincode(invalidPincode);

        Set<ConstraintViolation<AddressDTO>> violations = validator.validateProperty(addressDTO, "pincode");

        assertFalse(violations.isEmpty(), "Expected violations for pincode=" + invalidPincode);
    }

    @Test
    void testPincodeValidationWhenValueIsValid() {
        Set<ConstraintViolation<AddressDTO>> violations = validator.validateProperty(addressDTO, "pincode");

        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   ", INVALID_ADDRESS_TYPE})
    void testAddressTypeValidationWhenValueIsInvalid(String invalidAddressType) {
        addressDTO.setAddressType(invalidAddressType);

        Set<ConstraintViolation<AddressDTO>> violations = validator.validateProperty(addressDTO, "addressType");

        assertFalse(violations.isEmpty(), "Expected violations for addressType=" + invalidAddressType);
    }

    @ParameterizedTest
    @ValueSource(strings = {VALID_ADDRESS_TYPE, "OFFICE", "BILLING", "SHIPPING"})
    void testAddressTypeValidationWhenValueIsValid(String validAddressType) {
        addressDTO.setAddressType(validAddressType);

        Set<ConstraintViolation<AddressDTO>> violations = validator.validateProperty(addressDTO, "addressType");

        assertTrue(violations.isEmpty(), "Expected no violations for addressType=" + validAddressType);
    }

    @Test
    void testOptionalFieldsValidationWhenValueIsNull() {
        addressDTO.setBuildingName(null);
        addressDTO.setLandmark(null);
        addressDTO.setLocality(null);
        addressDTO.setVillage(null);
        addressDTO.setDistrict(null);

        Set<ConstraintViolation<AddressDTO>> violations = validator.validate(addressDTO);

        assertTrue(violations.isEmpty(), "Expected no violations since optional fields have no constraints");
    }
}