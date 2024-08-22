package com.website.e_commerce.customer.controller;

import com.website.e_commerce.customer.model.entity.CustomerEntity;
import com.website.e_commerce.customer.service.CustomerEntityUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
/**
 * The {@code CustomerEntityUserController} class is a REST controller that handles HTTP requests related to customer entities.
 * It provides endpoints to retrieve the current authenticated customer and to list all customer entities.
 *
 * <p>This controller uses {@link CustomerEntityUserService} to interact with the customer data.</p>
 *
 * @see CustomerEntityUserService
 */
@Controller
@RequestMapping("/users")
public class CustomerEntityUserController {
    private final CustomerEntityUserService customerEntityUserService;

    public CustomerEntityUserController(CustomerEntityUserService customerEntityUserService) {
        this.customerEntityUserService = customerEntityUserService;
    }
    /**
     * Retrieves the current authenticated {@link CustomerEntity} user.
     *
     * <p>This endpoint returns the authenticated customer entity with an HTTP status of {@link HttpStatus#OK OK}.</p>
     *
     * @return a {@link ResponseEntity} containing the authenticated customer entity
     */
    @GetMapping("/me")
    public ResponseEntity<CustomerEntity> authenticatedCustomerEntityUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerEntity currentCustomerUser = (CustomerEntity) authentication.getPrincipal();
        return ResponseEntity.ok(currentCustomerUser);
    }
    /**
     * Retrieves a list of all {@link CustomerEntity} users.
     *
     * <p>This endpoint returns a list of all customer entities with an HTTP status of {@link HttpStatus#OK OK}.</p>
     *
     * @return a {@link ResponseEntity} containing the list of all customer entities
     */
    @GetMapping()
    public ResponseEntity<List<CustomerEntity>>allUsers(){
        List<CustomerEntity> customerUser = customerEntityUserService.allUsers();
        return ResponseEntity.ok(customerUser);
    }
}
