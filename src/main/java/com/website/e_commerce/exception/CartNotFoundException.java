package com.website.e_commerce.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
     super("cart not found");
    }
}
