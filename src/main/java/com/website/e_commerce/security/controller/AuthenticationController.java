package com.website.e_commerce.security.controller;

import com.website.e_commerce.customer.model.dto.CustomerEntityLoginResponse;
import com.website.e_commerce.customer.model.dto.LoginCustomerEntityDto;
import com.website.e_commerce.customer.model.dto.RegisterCustomerEntityDto;
import com.website.e_commerce.customer.model.entity.CustomerEntity;
import com.website.e_commerce.security.service.AuthenticationService;
import com.website.e_commerce.security.service.JwtService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("auth")
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerEntity> register( @Valid @RequestBody  RegisterCustomerEntityDto registerUserDto) {
        CustomerEntity registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }
    @GetMapping("/signup")
    public String signupPage(){
        log.info("GET /auth/signup endpoint hit");
        return "/static/signup.html";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<CustomerEntityLoginResponse> authenticate(@RequestBody LoginCustomerEntityDto loginUserDto) {

        log.info("POST /auth/login endpoint hit with payload: {}", loginUserDto);

        CustomerEntity authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        CustomerEntityLoginResponse loginResponse = new CustomerEntityLoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresAt(jwtService.getExpirationTime());
        log.info("User authenticated successfully, token generated.");

        return ResponseEntity.ok(loginResponse);

    }

    @RequestMapping(value = "/signin" , method = {RequestMethod.GET})
    public String loginPage(){
        log.info("GET /auth/login endpoint hit");
        return "/static/login.html";
    }


}
