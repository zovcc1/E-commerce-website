package com.website.e_commerce.exception;

public class ProductNotFoundException extends RuntimeException  {
    public ProductNotFoundException() {
        super("product not found");
    }
}
