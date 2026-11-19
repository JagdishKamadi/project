package com.epam.controller;

import com.epam.dto.AddressDTO;
import com.epam.dto.EmployeeDTO;
import com.epam.exception.EmployeeNotFoundException;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    private String employeeDTOJSON;
    private EmployeeDTO employeeDTO;
    private final ObjectMapper objectMapper = new ObjectMapper();

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
    void testCreateEmployeeWhenInputIsValid() throws Exception {
        when(employeeService.createEmployee(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/employees")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(employeeDTOJSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateEmployeeWhenInputIsInvalid() throws Exception {
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

    @Test
    void testGetAllEmployeesWhenEmployeesExist() throws Exception {
        List<EmployeeDTO> employeeDTOS = Arrays.asList(employeeDTO, employeeDTO);
        when(employeeService.getAllEmployees()).thenReturn(employeeDTOS);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/v1/employees").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        String contentAsString = response.getContentAsString();
        assertAll(() -> assertTrue(contentAsString.contains("Joy")),
                () -> assertTrue(contentAsString.contains("Pune")),
                () -> assertTrue(contentAsString.contains("HOME"))
        );
    }

    @Test
    void testGetEmployeeWhenEmployeeIdIsValid() throws Exception {
        Integer employeeId = 1;
        when(employeeService.getEmployee(employeeId)).thenReturn(employeeDTO);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/v1/employees/{employeeId}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        String contentAsString = response.getContentAsString();
        assertAll(() -> assertTrue(contentAsString.contains("Joy")),
                () -> assertTrue(contentAsString.contains("joy@gmail.com")),
                () -> assertTrue(contentAsString.contains("Pune")),
                () -> assertTrue(contentAsString.contains("HOME"))
        );
        verify(employeeService).getEmployee(employeeId);
    }

    @Test
    void testUpdateEmployeeWhenEmployeeIdIsValid() throws Exception {
        Integer employeeId = 1;
        when(employeeService.updateEmployee(employeeId, employeeDTO)).thenReturn(employeeDTO);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.put("/v1/employees/{employeeId}", employeeId)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(employeeDTOJSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        String contentAsString = response.getContentAsString();
        assertAll(() -> assertTrue(contentAsString.contains("Joy")),
                () -> assertTrue(contentAsString.contains("joy@gmail.com")),
                () -> assertTrue(contentAsString.contains("Pune")),
                () -> assertTrue(contentAsString.contains("HOME"))
        );
        verify(employeeService).updateEmployee(employeeId, employeeDTO);
    }

    @Test
    void testUpdateEmployeeWhenEmployeeNotFound() throws Exception {
        Integer employeeId = 11;
        when(employeeService.updateEmployee(employeeId, employeeDTO)).thenThrow(EmployeeNotFoundException.class);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.put("/v1/employees/{employeeId}", employeeId)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(employeeDTOJSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();

        assertTrue(response.getContentAsString().contains("Employee Not Found"));
        verify(employeeService).updateEmployee(employeeId, employeeDTO);
    }

    @Test
    void testDeleteEmployeeWhenEmployeeIdIsValid() throws Exception {
        Integer employeeId = 1;

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/employees/{employeeId}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse();

        assertTrue(response.getContentAsString().contains("Employee deleted for id " + employeeId));
        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }
}