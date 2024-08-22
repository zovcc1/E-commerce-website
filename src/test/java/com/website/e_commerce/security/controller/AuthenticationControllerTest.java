package com.website.e_commerce.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.website.e_commerce.customer.model.dto.LoginCustomerEntityDto;
import com.website.e_commerce.customer.model.dto.RegisterCustomerEntityDto;
import com.website.e_commerce.customer.model.entity.CustomerEntity;
import com.website.e_commerce.security.service.AuthenticationService;
import com.website.e_commerce.security.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private MockMvc mvc;
    private ObjectMapper objectMapper;
    private RegisterCustomerEntityDto registerCustomerEntityDto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        registerCustomerEntityDto = RegisterCustomerEntityDto.builder()
                .firstName("Jon")
                .lastName("Doe")
                .email("JonDoe@example.com")
                .password("Jfa8u3uhf!@") // Ensure this meets the validation requirements
                .address("aHkej jfa23")
                .phoneNumber("999999999")
                .build();

        mvc = MockMvcBuilders.standaloneSetup(authenticationController)
                .build();
    }

    @Test
    void AuthenticationController_Register_ShouldReturnOkay() throws Exception {
//         Arrange
        CustomerEntity customerEntity = CustomerEntity.builder()
                .firstName("Jon")
                .lastName("Doe")
                .email("JonDoe@example.com")
                .address("aHkej jfa23")
                .phoneNumber("999999999")
                .build();

        when(authenticationService.signup(registerCustomerEntityDto))
                .thenReturn(customerEntity);

        String jsonContent = objectMapper.writeValueAsString(registerCustomerEntityDto);

        // Act & Assert
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Jon"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("JonDoe@example.com"))
                .andExpect(jsonPath("$.address").value("aHkej jfa23"))
                .andExpect(jsonPath("$.phoneNumber").value("999999999"));
    }

    @Test
    void authenticationController_authenticate_ShouldReturnOk() throws Exception {
        // Arrange
        String email = "Jon@example.com";
        String password = "Hd284&5dlaifj3j";

        // Create an authenticated customer entity
        CustomerEntity authenticatedCustomer = CustomerEntity.builder()
                .id(1L)
                .firstName("Jon")
                .lastName("Doe")
                .email(email)
                .password(password)
                .address("jfa H Street")
                .phoneNumber("9990999999")
                .build();

        // Generate a JWT token (mocked)
        String jwtToken = "mocked.jwt.token";
        long expirationTime = System.currentTimeMillis() + 3600000; // Example expiration time

        // Create the login DTO
        LoginCustomerEntityDto loginDto = LoginCustomerEntityDto.builder()
                .email(email)
                .password(password)
                .build();

        // Mock the AuthenticationService to return the authenticatedCustomer
        doReturn(authenticatedCustomer).when(authenticationService).authenticate(loginDto);

        // Mock the JwtService to return the JWT token and expiration time
        doReturn(jwtToken).when(jwtService).generateToken(authenticatedCustomer);
        doReturn(expirationTime).when(jwtService).getExpirationTime();

        // Convert loginDto to JSON string
        String jsonContent = objectMapper.writeValueAsString(loginDto);

        // Act & Assert
        mvc.perform(MockMvcRequestBuilders.post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value(jwtToken))
                .andExpect(jsonPath("$.expiresAt").value(expirationTime));
    }


}
