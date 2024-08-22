package com.website.e_commerce.security.config;

import com.website.e_commerce.customer.model.entity.CustomerEntity;
import com.website.e_commerce.customer.repository.CustomerEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationConfigurationTest {
    @Mock
    private CustomerEntityRepository customerEntityRepository;

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;
    @InjectMocks
    private ApplicationConfiguration applicationConfiguration;




    @BeforeEach
    void setUp() {
        applicationConfiguration = new ApplicationConfiguration(customerEntityRepository);
    }

    @Test
    void applicationConfiguration_LoadUserDetails_ReturnUserDetails() {
        //Arrange
       String customerUserName = "Jon@example.com";
        UserDetailsService userDetailsService = applicationConfiguration.loadUserDetails();
       //Act
       Mockito.when(customerEntityRepository.findByEmail(customerUserName)).thenReturn(Optional.of(mock(CustomerEntity.class)));
        UserDetails userDetails = userDetailsService.loadUserByUsername(customerUserName);
        //Assert
        Assertions.assertThat(userDetailsService).isNotNull();


    }


    @Test
    void applicationConfiguration_LoadUserDetails_UserDoesntExist_ShouldReturnUsernameNotFoundException() {
        // Arrange
        String nonExistentEmail = "nonexistent@example.com";
        UserDetailsService userDetailsService = applicationConfiguration.loadUserDetails();

        Mockito.when(customerEntityRepository.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThatThrownBy(() -> userDetailsService.loadUserByUsername(nonExistentEmail))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("user not found");
    }

    @Test
    void passwordEncoder() {
    PasswordEncoder passwordEncoder = applicationConfiguration.passwordEncoder();
    Assertions.assertThat(passwordEncoder).isNotNull();
    }

    @Test
    void authenticationManager() throws Exception {
        // Given
        AuthenticationConfiguration mockConfig = mock(AuthenticationConfiguration.class);
        AuthenticationManager mockManager = mock(AuthenticationManager.class);
        when(mockConfig.getAuthenticationManager()).thenReturn(mockManager);

        // Act
        AuthenticationManager authenticationManager = applicationConfiguration.authenticationManager(mockConfig);

        // Assert
        Assertions.assertThat(authenticationManager).isNotNull();
    }


    @Test
    void authenticationProvider() {
        AuthenticationProvider authenticationProvider = applicationConfiguration.authenticationProvider();
        Assertions.assertThat(authenticationProvider).isNotNull();



    }
}