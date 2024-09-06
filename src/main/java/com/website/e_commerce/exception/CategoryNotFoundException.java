package com.website.e_commerce.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException() {
        super("category not found");
    }
}
