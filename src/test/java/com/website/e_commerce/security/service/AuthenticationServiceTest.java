package com.website.e_commerce.security.service;

import com.website.e_commerce.customer.model.dto.LoginCustomerEntityDto;
import com.website.e_commerce.customer.model.dto.RegisterCustomerEntityDto;
import com.website.e_commerce.customer.model.entity.CustomerEntity;
import com.website.e_commerce.customer.repository.CustomerEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
   @Mock
    private CustomerEntityRepository customerEntityRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
      private AuthenticationService authenticationService;


    @Test
    void authenticationService_Signup_ShouldReturnCustomerEntity() {
        RegisterCustomerEntityDto input = RegisterCustomerEntityDto.builder()
                .firstName("Jon")
                .lastName("Doe")
                .email("JonDoe23@example.com")
                .password("hellWojf&&@w2323")
                .address("Address S Street")
                .phoneNumber("099199919991")
                .build();

         CustomerEntity customerEntity = CustomerEntity.builder()
                 .id(1L)
                 .firstName(input.getFirstName())
                 .lastName(input.getLastName())
                 .email(input.getEmail())
                 .password(passwordEncoder.encode(input.getPassword()))
                 .address(input.getAddress())
                 .phoneNumber(input.getPhoneNumber())
                 .build();

        //  Act

        when(customerEntityRepository.save(customerEntity)).thenReturn(customerEntity);
          CustomerEntity customer = customerEntityRepository.save(customerEntity);
        //Assert
        Assertions.assertThat(customer).isNotNull();

    }

    @Test
    void AuthenticationService_AuthenticateSignIn_ShouldReturnCustomerEntity() {
        // Arrange
        LoginCustomerEntityDto loginDTO =  LoginCustomerEntityDto
                .builder()
                        .password("HelloAofjfa24@")
                                .email("JonDoe@em.com")
                                        .build();

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail("john.doe@example.com");
        when(customerEntityRepository.findByEmail(any(String.class))).thenReturn(Optional.of(customerEntity));

        // Act
        CustomerEntity result = authenticationService.authenticate(loginDTO);

        // Assert
        Assertions.assertThat(result.getEmail()).isNotNull();
        assertEquals("john.doe@example.com", result.getEmail());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

    }

    @Test
    void AuthenticationService_Authenticate_ShouldReturnUsernameNotFoundException() {
        // Arrange
        LoginCustomerEntityDto loginCustomerEntityDTO = LoginCustomerEntityDto.builder()
                .email("JonDoe@example.com")
                .password("HeloWorld.#3r")
                .build();

        // Act
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(UsernameNotFoundException.class);

        // Assert
        Assertions.assertThatThrownBy(() ->
                        authenticationService.authenticate(loginCustomerEntityDTO))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}