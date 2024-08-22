package com.website.e_commerce.customer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntityLoginResponse {

    private String  token;
    private long expiresAt;


}
