package com.progra3.cafeteria_api.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Long id) {
        super("Not enough stock available for product with ID: " + id);
    }
}
