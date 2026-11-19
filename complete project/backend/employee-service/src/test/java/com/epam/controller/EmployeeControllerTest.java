package com.epam.controller;

import com.epam.dto.AddressDTO;
import com.epam.dto.EmployeeDTO;
import com.epam.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    private String employeeDTOJSON;
    private EmployeeDTO employeeDTO;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        employeeDTO = EmployeeDTO.builder()
                .id(1)
                .firstName("Joy")
                .lastName("Smith")
                .email("joy@gmail.com")
                .age(21)
                .address(AddressDTO.builder()
                        .houseNumber("12A")
                        .street("MG Road")
                        .city("Pune")
                        .state("Maharashtra")
                        .country("India")
                        .pincode("411001")
                        .addressType("HOME")
                        .build())
                .build();

        employeeDTOJSON = objectMapper.writeValueAsString(employeeDTO);
    }

    @Test
    void testCreateEmployeeEndpoint() throws Exception {
        when(employeeService.createEmployee(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/employees")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(employeeDTOJSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateEmployeeEndpointThrowExceptionWhenObjectIsNotValid() throws Exception {
        employeeDTO.setFirstName("");
        employeeDTOJSON = objectMapper.writeValueAsString(employeeDTO);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/v1/employees")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(employeeDTOJSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse();
        String responseBody = response.getContentAsString();
        System.out.println(responseBody);
        assertTrue(responseBody.contains("firstName should not be blank"));
    }
}