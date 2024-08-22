package com.website.e_commerce.customer.model.dto;

import com.website.e_commerce.annotation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class RegisterCustomerEntityDto {
    @NotBlank(message = "first name is a mandatory")
    private String firstName;
    @NotBlank(message = "last name is mandatory")
    private String lastName;
    @NotBlank(message = "email is a mandatory")
    @Email(message = "must be a well-formed email address")
    private String email;
    @NotBlank(message = "password is a mandatory")
    @Password
    private String password;
    @NotBlank(message = "address is a mandatory")
    private String address;
    @NotBlank(message = "phone number is a mandatory")
    private String phoneNumber;

}
