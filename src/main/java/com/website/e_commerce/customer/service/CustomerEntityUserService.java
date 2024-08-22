package com.website.e_commerce.customer.service;

import com.website.e_commerce.customer.model.entity.CustomerEntity;
import com.website.e_commerce.customer.repository.CustomerEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerEntityUserService {
    private final CustomerEntityRepository customerEntityRepository;

    public CustomerEntityUserService(CustomerEntityRepository customerEntityRepository) {
        this.customerEntityRepository = customerEntityRepository;
    }


    public List<CustomerEntity> allUsers() {
        List<CustomerEntity> customers = new ArrayList<>();

        customerEntityRepository.findAll().forEach(customers::add);

        return customers;
    }

    public CustomerEntity findByEmail(String email) {

        return customerEntityRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("user not found"));
    }
}
