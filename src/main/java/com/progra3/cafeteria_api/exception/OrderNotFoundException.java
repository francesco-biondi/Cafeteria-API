package com.progra3.cafeteria_api.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("No orders found for ID: " + id);
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
