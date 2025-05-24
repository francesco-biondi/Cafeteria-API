package com.progra3.cafeteria_api.exception;

public class ProductOptionNotFoundException extends RuntimeException {
    public ProductOptionNotFoundException(Long id) {
        super("Product option not found for ID: " + id);
    }
}
