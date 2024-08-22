package com.website.e_commerce.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.website.e_commerce.customer.model.entity.CustomerEntity;
import com.website.e_commerce.customer.service.CustomerEntityUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerEntityUserControllerTest {
    @Mock
    private CustomerEntityUserService customerEntityUserService;
    @InjectMocks
    private CustomerEntityUserController controller;
    private MockMvc mvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
          objectMapper = new ObjectMapper();


    }

    @Test
    void CustomerEntityUserController_AuthenticatedCustomerUser_ShouldReturnOk() throws Exception {
        // Arrange
        CustomerEntity mockCustomer = new CustomerEntity();
        mockCustomer.setId(1L);
        mockCustomer.setEmail("test@example.com");
        mockCustomer.setAddress("123 Test Street");
        mockCustomer.setPhoneNumber("1234567890");

        Authentication authentication = new UsernamePasswordAuthenticationToken(mockCustomer.getEmail(), mockCustomer.getPassword());
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(customerEntityUserService.allUsers()).thenReturn(Collections.singletonList(mockCustomer));

        // Act
        mvc.perform(get("/users/me")
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.address").value("123 Test Street"))
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"));




    }
}