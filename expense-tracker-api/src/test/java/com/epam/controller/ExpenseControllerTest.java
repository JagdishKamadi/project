package com.epam.controller;

import com.epam.constant.Category;
import com.epam.filter.JwtAuthenticationFilter;
import com.epam.model.AppUser;
import com.epam.model.Expense;
import com.epam.securityservice.JWTTokenManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Please note that currently this test case is calling real method
 * will implement the mock behavior of it soon
 */
@SpringBootTest
@AutoConfigureMockMvc
class ExpenseControllerTest {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JWTTokenManagerService jwtTokenManagerService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private AppUser appUser;

    private Expense expense;

    private String expenseAsJSONString;

    private String token;

    @BeforeEach
    public void setup() throws Exception {
        objectMapper = new ObjectMapper();

        appUser = AppUser.builder()
                .id(1)
                .fullName("Jagdish Kamadi")
                .email("JagdishKamadi@gmail.com")
                .password("1234").build();


        expense = Expense.builder()
                .description("List of medicine")
                .amount(150D)
                .category(Category.HEALTH)
                .build();

        expenseAsJSONString = objectMapper.writeValueAsString(expense);
        token = jwtTokenManagerService.generateToken(appUser);
    }

    @Test
    void addTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/expenses/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(expenseAsJSONString)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isCreated());
    }

    @Test
    void removeTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/expenses/remove/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isFound());
    }

    @Test
    void updateTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/expenses/update/{id}", 1)
                .content(expenseAsJSONString)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }
}