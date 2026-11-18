package com.epam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmployeeTest {

    private static final Integer VALID_ID = 1;
    private static final String VALID_FIRST_NAME = "Jack";
    private static final String VALID_LAST_NAME = "Admin";
    private static final String VALID_EMAIL = "jack@gmail.com";
    private static final Integer VALID_AGE = 21;
    private static final LocalDateTime VALID_CREATED_AT = LocalDateTime.of(2026, 1, 1, 10, 0);
    private static final LocalDateTime VALID_UPDATED_AT = LocalDateTime.of(2026, 1, 2, 11, 0);

    private static final String VALID_HOUSE_NUMBER = "12A";
    private static final String VALID_STREET = "MG Road";
    private static final String VALID_CITY = "Pune";
    private static final String VALID_STATE = "Maharashtra";
    private static final String VALID_COUNTRY = "India";
    private static final String VALID_PINCODE = "411001";
    private static final String VALID_ADDRESS_TYPE = "HOME";

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = buildValidEmployee();
    }

    private Employee buildValidEmployee() {
        return Employee.builder()
                .id(VALID_ID)
                .firstName(VALID_FIRST_NAME)
                .lastName(VALID_LAST_NAME)
                .email(VALID_EMAIL)
                .age(VALID_AGE)
                .createdAt(VALID_CREATED_AT)
                .updatedAt(VALID_UPDATED_AT)
                .address(buildValidAddress())
                .build();
    }

    private Address buildValidAddress() {
        return Address.builder()
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
        assertAll(() -> assertEquals(VALID_ID, employee.getId()),
                () -> assertEquals(VALID_FIRST_NAME, employee.getFirstName()),
                () -> assertEquals(VALID_LAST_NAME, employee.getLastName()),
                () -> assertEquals(VALID_EMAIL, employee.getEmail()),
                () -> assertEquals(VALID_AGE, employee.getAge()),
                () -> assertEquals(VALID_CREATED_AT, employee.getCreatedAt()),
                () -> assertEquals(VALID_UPDATED_AT, employee.getUpdatedAt()),
                () -> assertNotNull(employee.getAddress()));
    }

    @Test
    void testNoArgsConstructorWhenInvoked() {
        Employee emptyEmployee = new Employee();

        assertAll(() -> assertNull(emptyEmployee.getId()),
                () -> assertNull(emptyEmployee.getFirstName()),
                () -> assertNull(emptyEmployee.getLastName()),
                () -> assertNull(emptyEmployee.getEmail()),
                () -> assertNull(emptyEmployee.getAge()),
                () -> assertNull(emptyEmployee.getCreatedAt()),
                () -> assertNull(emptyEmployee.getUpdatedAt()),
                () -> assertNull(emptyEmployee.getAddress()));
    }

    @Test
    void testAllArgsConstructorWhenAllFieldsProvided() {
        Address address = buildValidAddress();

        Employee allArgsEmployee = new Employee(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL, VALID_AGE,
                VALID_CREATED_AT, VALID_UPDATED_AT, address);

        assertAll(() -> assertEquals(VALID_ID, allArgsEmployee.getId()),
                () -> assertEquals(VALID_FIRST_NAME, allArgsEmployee.getFirstName()),
                () -> assertEquals(VALID_LAST_NAME, allArgsEmployee.getLastName()),
                () -> assertEquals(VALID_EMAIL, allArgsEmployee.getEmail()),
                () -> assertEquals(VALID_AGE, allArgsEmployee.getAge()),
                () -> assertEquals(VALID_CREATED_AT, allArgsEmployee.getCreatedAt()),
                () -> assertEquals(VALID_UPDATED_AT, allArgsEmployee.getUpdatedAt()),
                () -> assertEquals(address, allArgsEmployee.getAddress()));
    }

    @Test
    void testSettersAndGettersWhenValuesAreChanged() {
        Address newAddress = buildValidAddress();
        LocalDateTime newCreatedAt = LocalDateTime.of(2026, 2, 1, 9, 0);
        LocalDateTime newUpdatedAt = LocalDateTime.of(2026, 2, 2, 10, 0);

        employee.setId(2);
        employee.setFirstName("Jenny");
        employee.setLastName("User");
        employee.setEmail("jenny@gmail.com");
        employee.setAge(25);
        employee.setCreatedAt(newCreatedAt);
        employee.setUpdatedAt(newUpdatedAt);
        employee.setAddress(newAddress);

        assertAll(() -> assertEquals(2, employee.getId()),
                () -> assertEquals("Jenny", employee.getFirstName()),
                () -> assertEquals("User", employee.getLastName()),
                () -> assertEquals("jenny@gmail.com", employee.getEmail()),
                () -> assertEquals(25, employee.getAge()),
                () -> assertEquals(newCreatedAt, employee.getCreatedAt()),
                () -> assertEquals(newUpdatedAt, employee.getUpdatedAt()),
                () -> assertEquals(newAddress, employee.getAddress()));
    }

    @Test
    void testSetAddressWhenValueIsNull() {
        employee.setAddress(null);

        assertNull(employee.getAddress());
    }

    @Test
    void testEqualsWhenComparedWithSameReference() {
        Object sameReference = employee;

        assertEquals(employee, sameReference);
    }

    @Test
    void testEqualsWhenComparedWithEqualObject() {
        Employee other = buildValidEmployee();

        assertEquals(employee, other);
        assertEquals(employee.hashCode(), other.hashCode());
    }

    @Test
    void testEqualsWhenComparedWithDifferentValues() {
        Employee other = buildValidEmployee();
        other.setFirstName("Jenny");

        assertNotEquals(employee, other);
    }

    @Test
    void testEqualsWhenComparedWithNull() {
        assertNotEquals(null, employee);
    }

    @Test
    void testEqualsWhenComparedWithDifferentClass() {
        assertNotEquals(new Object(), employee);
    }

    @Test
    void testToStringWhenInvoked() {
        String result = employee.toString();

        assertTrue(result.contains(VALID_FIRST_NAME));
    }
}