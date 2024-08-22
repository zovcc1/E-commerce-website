package com.website.e_commerce.customer.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginCustomerEntityDto {
    private String email;
    private String password;

}
