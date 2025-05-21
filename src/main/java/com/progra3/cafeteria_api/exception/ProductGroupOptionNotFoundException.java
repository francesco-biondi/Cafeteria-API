package com.progra3.cafeteria_api.exception;

public class ProductGroupOptionNotFoundException extends RuntimeException {
    public ProductGroupOptionNotFoundException(Long id) {
        super("Product group option not found for ID: " + id);
    }
}
