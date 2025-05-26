package com.progra3.cafeteria_api.exception;

public class ProductGroupNotFoundException extends RuntimeException {
    public ProductGroupNotFoundException(String message) {
        super(message);
    }

    public ProductGroupNotFoundException(Long id) {
        super("No product group found for ID: " + id);
    }
}
