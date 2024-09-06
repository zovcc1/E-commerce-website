package com.website.e_commerce.cart.model.dto;

import com.website.e_commerce.product.model.entity.Product;
import lombok.Data;

import java.util.Set;

@Data
public class CartDto {
    private Long id;
    private Set<Product> products;
}
