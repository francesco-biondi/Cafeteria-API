package com.progra3.cafeteria_api.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Exception thrown when a category cannot be deleted")
public class CannotDeleteCategoryException extends RuntimeException {
    public CannotDeleteCategoryException(String message) {
        super(message);
    }
}
