package com.progra3.cafeteria_api.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Exception thrown when a product is not found")
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
