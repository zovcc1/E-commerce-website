package com.website.e_commerce.exception;

public class ProductAlreadyExistException extends RuntimeException{
    public ProductAlreadyExistException() {
        super("product already created");
    }
}
