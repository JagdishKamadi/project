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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmployeeDTOTest {

    private static final Integer VALID_ID = 1;
    private static final String VALID_FIRST_NAME = "Jack";
    private static final String VALID_LAST_NAME = "Admin";
    private static final String VALID_EMAIL = "jack@gmail.com";
    private static final Integer VALID_AGE = 21;
    private static final Integer MIN_VALID_AGE = 18;
    private static final Integer BELOW_MIN_AGE = 17;
    private static final String INVALID_EMAIL_FORMAT = "invalid-email-format";
    private static final String BLANK_VALUE = "   ";

    private static final String VALID_HOUSE_NUMBER = "12A";
    private static final String VALID_STREET = "MG Road";
    private static final String VALID_CITY = "Pune";
    private static final String VALID_STATE = "Maharashtra";
    private static final String VALID_COUNTRY = "India";
    private static final String VALID_PINCODE = "411001";
    private static final String VALID_ADDRESS_TYPE = "HOME";

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    private EmployeeDTO employeeDTO;

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
        employeeDTO = EmployeeDTO.builder()
                .id(VALID_ID)
                .firstName(VALID_FIRST_NAME)
                .lastName(VALID_LAST_NAME)
                .email(VALID_EMAIL)
                .age(VALID_AGE)
                .address(buildValidAddress())
                .build();
    }

    private AddressDTO buildValidAddress() {
        return AddressDTO.builder()
                .houseNumber(VALID_HOUSE_NUMBER)
                .street(VALID_STREET)
                .city(VALID_CITY)
                .state(VALID_STATE)
                .country(VALID_COUNTRY)
                .pincode(VALID_PINCODE)
                .addressType(VALID_ADDRESS_TYPE)
                .build();
    }

    @Test
    void testBuilderWhenAllFieldsAreProvided() {
        assertAll(() -> assertEquals(VALID_ID, employeeDTO.getId()),
                () -> assertEquals(VALID_FIRST_NAME, employeeDTO.getFirstName()),
                () -> assertEquals(VALID_LAST_NAME, employeeDTO.getLastName()),
                () -> assertEquals(VALID_EMAIL, employeeDTO.getEmail()),
                () -> assertEquals(VALID_AGE, employeeDTO.getAge()),
                () -> assertNotNull(employeeDTO.getAddress()));
    }

    @Test
    void testNoArgsConstructorWhenInvoked() {
        EmployeeDTO emptyEmployeeDTO = new EmployeeDTO();

        assertAll(() -> assertNull(emptyEmployeeDTO.getId()),
                () -> assertNull(emptyEmployeeDTO.getFirstName()),
                () -> assertNull(emptyEmployeeDTO.getLastName()),
                () -> assertNull(emptyEmployeeDTO.getEmail()),
                () -> assertNull(emptyEmployeeDTO.getAge()),
                () -> assertNull(emptyEmployeeDTO.getAddress()));
    }

    @Test
    void testAllArgsConstructorWhenAllFieldsProvided() {
        AddressDTO address = buildValidAddress();

        EmployeeDTO employee = new EmployeeDTO(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL, VALID_AGE, address);

        assertAll(() -> assertEquals(VALID_ID, employee.getId()),
                () -> assertEquals(VALID_FIRST_NAME, employee.getFirstName()),
                () -> assertEquals(VALID_LAST_NAME, employee.getLastName()),
                () -> assertEquals(VALID_EMAIL, employee.getEmail()),
                () -> assertEquals(VALID_AGE, employee.getAge()),
                () -> assertEquals(address, employee.getAddress()));
    }

    @Test
    void testSettersAndGettersWhenValuesAreChanged() {
        AddressDTO newAddress = buildValidAddress();

        employeeDTO.setId(2);
        employeeDTO.setFirstName("Jenny");
        employeeDTO.setLastName("User");
        employeeDTO.setEmail("jenny@gmail.com");
        employeeDTO.setAge(25);
        employeeDTO.setAddress(newAddress);

        assertAll(() -> assertEquals(2, employeeDTO.getId()),
                () -> assertEquals("Jenny", employeeDTO.getFirstName()),
                () -> assertEquals("User", employeeDTO.getLastName()),
                () -> assertEquals("jenny@gmail.com", employeeDTO.getEmail()),
                () -> assertEquals(25, employeeDTO.getAge()),
                () -> assertEquals(newAddress, employeeDTO.getAddress()));
    }

    @Test
    void testEqualsWhenComparedWithSameReference() {
        Object sameReference = employeeDTO;

        assertEquals(employeeDTO, sameReference);
    }

    @Test
    void testEqualsWhenComparedWithEqualObject() {
        EmployeeDTO other = EmployeeDTO.builder()
                .id(VALID_ID)
                .firstName(VALID_FIRST_NAME)
                .lastName(VALID_LAST_NAME)
                .email(VALID_EMAIL)
                .age(VALID_AGE)
                .address(buildValidAddress())
                .build();

        assertEquals(employeeDTO, other);
        assertEquals(employeeDTO.hashCode(), other.hashCode());
    }

    @Test
    void testEqualsWhenComparedWithDifferentValues() {
        EmployeeDTO other = EmployeeDTO.builder()
                .id(2)
                .firstName("Jenny")
                .lastName(VALID_LAST_NAME)
                .email(VALID_EMAIL)
                .age(VALID_AGE)
                .address(buildValidAddress())
                .build();

        assertNotEquals(employeeDTO, other);
    }

    @Test
    void testEqualsWhenComparedWithNull() {
        assertNotEquals(null, employeeDTO);
    }

    @Test
    void testEqualsWhenComparedWithDifferentClass() {
        assertNotEquals(new Object(), employeeDTO);
    }

    @Test
    void testToStringWhenInvoked() {
        String result = employeeDTO.toString();

        assertTrue(result.contains(VALID_FIRST_NAME));
    }

    @Test
    void testValidationWhenAllFieldsAreValid() {
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);

        assertTrue(violations.isEmpty(), "Expected no violations for a fully valid EmployeeDTO");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void testFirstNameValidationWhenValueIsBlankOrNull(String invalidFirstName) {
        employeeDTO.setFirstName(invalidFirstName);

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validateProperty(employeeDTO, "firstName");

        assertFalse(violations.isEmpty(), "Expected violations for blank/null firstName");
    }

    @ParameterizedTest
    @ValueSource(strings = {VALID_FIRST_NAME, "Jenny"})
    void testFirstNameValidationWhenValueIsValid(String validFirstName) {
        employeeDTO.setFirstName(validFirstName);

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validateProperty(employeeDTO, "firstName");

        assertTrue(violations.isEmpty(), "Expected no violations for firstName=" + validFirstName);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void testLastNameValidationWhenValueIsBlankOrNull(String invalidLastName) {
        employeeDTO.setLastName(invalidLastName);

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validateProperty(employeeDTO, "lastName");

        assertFalse(violations.isEmpty(), "Expected violations for blank/null lastName");
    }

    @Test
    void testLastNameValidationWhenValueIsValid() {
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validateProperty(employeeDTO, "lastName");

        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   ", INVALID_EMAIL_FORMAT})
    void testEmailValidationWhenValueIsInvalid(String invalidEmail) {
        employeeDTO.setEmail(invalidEmail);

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validateProperty(employeeDTO, "email");

        assertFalse(violations.isEmpty(), "Expected violations for email=" + invalidEmail);
    }

    @Test
    void testEmailValidationWhenValueIsValid() {
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validateProperty(employeeDTO, "email");

        assertTrue(violations.isEmpty());
    }

    @Test
    void testAgeValidationWhenValueIsNull() {
        employeeDTO.setAge(null);

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validateProperty(employeeDTO, "age");

        assertFalse(violations.isEmpty(), "Expected violation for null age");
    }

    @Test
    void testAgeValidationWhenValueIsBelowMinimum() {
        employeeDTO.setAge(BELOW_MIN_AGE);

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validateProperty(employeeDTO, "age");

        assertFalse(violations.isEmpty(), "Expected violation for age below minimum");
    }

    @Test
    void testAgeValidationWhenValueIsAtMinimumBoundary() {
        employeeDTO.setAge(MIN_VALID_AGE);

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validateProperty(employeeDTO, "age");

        assertTrue(violations.isEmpty(), "Expected no violation at minimum boundary age");
    }

    @Test
    void testAgeValidationWhenValueIsValid() {
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validateProperty(employeeDTO, "age");

        assertTrue(violations.isEmpty());
    }

    @Test
    void testAddressValidationWhenValueIsNull() {
        employeeDTO.setAddress(null);

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validateProperty(employeeDTO, "address");

        assertFalse(violations.isEmpty(), "Expected violation for null address");
    }

    @Test
    void testAddressValidationWhenNestedFieldIsInvalid() {
        AddressDTO invalidAddress = buildValidAddress();
        invalidAddress.setPincode(BLANK_VALUE);
        employeeDTO.setAddress(invalidAddress);

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);

        assertFalse(violations.isEmpty(), "Expected cascaded violation for invalid nested address field");
    }

    @Test
    void testAddressValidationWhenValueIsValid() {
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validateProperty(employeeDTO, "address");

        assertTrue(violations.isEmpty());
    }
}