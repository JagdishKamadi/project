package com.epam.service;

import com.epam.dto.AddressDTO;
import com.epam.dto.EmployeeDTO;
import com.epam.model.Address;
import com.epam.model.Employee;
import com.epam.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tools.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    private static final Integer VALID_ID = 1;
    private static final String VALID_FIRST_NAME = "Jack";
    private static final String VALID_LAST_NAME = "Admin";
    private static final String VALID_EMAIL = "jack@gmail.com";
    private static final Integer VALID_AGE = 21;

    private static final String VALID_HOUSE_NUMBER = "12A";
    private static final String VALID_STREET = "MG Road";
    private static final String VALID_CITY = "Pune";
    private static final String VALID_STATE = "Maharashtra";
    private static final String VALID_COUNTRY = "India";
    private static final String VALID_PINCODE = "411001";
    private static final String VALID_ADDRESS_TYPE = "HOME";

    private EmployeeDTO employeeDTO;
    private Employee employee;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        employeeDTO = EmployeeDTO.builder()
                .id(VALID_ID)
                .firstName(VALID_FIRST_NAME)
                .lastName(VALID_LAST_NAME)
                .email(VALID_EMAIL)
                .age(VALID_AGE)
                .address(buildValidAddressDTO())
                .build();
        employee = Employee.builder()
                .id(VALID_ID)
                .firstName(VALID_FIRST_NAME)
                .lastName(VALID_LAST_NAME)
                .email(VALID_EMAIL)
                .age(VALID_AGE)
                .address(buildValidAddress())
                .build();
    }

    private AddressDTO buildValidAddressDTO() {
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
    void testCreateEmployee() {
        when(objectMapper.convertValue(employeeDTO, Employee.class)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(objectMapper.convertValue(employee, EmployeeDTO.class)).thenReturn(employeeDTO);

        EmployeeDTO result = employeeService.createEmployee(employeeDTO);

        assertEquals(employeeDTO, result);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employeeList = Collections.singletonList(employee);
        List<EmployeeDTO> expectedEmployeeDTOs = Collections.singletonList(employeeDTO);
        when(employeeRepository.findAll()).thenReturn(employeeList);
        when(objectMapper.convertValue(employee, EmployeeDTO.class)).thenReturn(employeeDTO);

        List<EmployeeDTO> allEmployees = employeeService.getAllEmployees();

        assertEquals(expectedEmployeeDTOs, allEmployees);
    }
}