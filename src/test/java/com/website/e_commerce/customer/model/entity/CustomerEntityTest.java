package com.website.e_commerce.customer.model.entity;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerEntityTest {

    private GrantedAuthority grantedAuthority;

    private CustomerEntity customerEntity;
    Validator validator;
    @BeforeEach
    void setUp() {

        customerEntity = CustomerEntity.builder()
                .id(1L)
                .firstName("Jon")
                .lastName("Doe")
                .email("JonDoe@example.com")
                .password("HelloWorldTHis^")
                .build();
    }

    @Test
    void CustomerEntity_GetAuthorities_ShouldReturnEmptyList() {
        // Act
        Collection<? extends GrantedAuthority> authorities = customerEntity.getAuthorities();
        // Assert
        assertThat(authorities).isNotNull();
        assertThat(authorities).isEmpty();
    }

    @Test
    void CustomerEntity_Getters_ShouldReturnCorrectValues() {
        // Act & Assert
        assertThat(customerEntity.getId()).isEqualTo(1L);
        assertThat(customerEntity.getFirstName()).isEqualTo("Jon");
        assertThat(customerEntity.getLastName()).isEqualTo("Doe");
        assertThat(customerEntity.getEmail()).isEqualTo("JonDoe@example.com");
        assertThat(customerEntity.getPassword()).isEqualTo("HelloWorldTHis^");
    }

    @Test
    void CustomerEntity_EqualsAndHashCode_ShouldWorkCorrectly() {
        // Arrange
        CustomerEntity sameCustomer = CustomerEntity.builder()
                .id(1L)
                .firstName("Jon")
                .lastName("Doe")
                .email("JonDoe@example.com")
                .password("HelloWorldTHis^")
                .build();

        CustomerEntity differentCustomer = CustomerEntity.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .email("JaneDoe@example.com")
                .password("DifferentPassword")
                .build();

        // Act & Assert
        assertThat(customerEntity).isEqualTo(sameCustomer);
        assertThat(customerEntity).hasSameHashCodeAs(sameCustomer);
        assertThat(customerEntity).isNotEqualTo(differentCustomer);
        assertThat(customerEntity.hashCode()).isNotEqualTo(differentCustomer.hashCode());
    }




}
