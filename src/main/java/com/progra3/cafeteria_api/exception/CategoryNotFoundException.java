package com.progra3.cafeteria_api.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Exception thrown when a category is not found")
public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}