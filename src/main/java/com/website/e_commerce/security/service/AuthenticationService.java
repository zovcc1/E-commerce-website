package com.website.e_commerce.security.service;

import com.website.e_commerce.customer.model.dto.LoginCustomerEntityDto;
import com.website.e_commerce.customer.model.dto.RegisterCustomerEntityDto;
import com.website.e_commerce.customer.model.entity.CustomerEntity;
import com.website.e_commerce.customer.repository.CustomerEntityRepository;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class AuthenticationService {
    private final CustomerEntityRepository  customerEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(CustomerEntityRepository customerEntityRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.customerEntityRepository = customerEntityRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public CustomerEntity signup(@Valid RegisterCustomerEntityDto input){
        CustomerEntity customerEntity =  new CustomerEntity();
              customerEntity.setFirstName(input.getFirstName());
                customerEntity.setLastName(input.getLastName());
                customerEntity.setEmail(input.getEmail());
                customerEntity.setPhoneNumber(input.getPhoneNumber());
                customerEntity.setAddress(input.getAddress());
        customerEntity.setPassword(passwordEncoder.encode(input.getPassword()));

        return customerEntityRepository.save(customerEntity);


    }
    public CustomerEntity authenticate(@Valid LoginCustomerEntityDto input){
       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail() , input.getPassword()));

         return customerEntityRepository.findByEmail(input.getEmail()).orElseThrow(()->new UsernameNotFoundException("user not found"));
    }


}
