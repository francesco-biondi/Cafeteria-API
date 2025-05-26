package com.progra3.cafeteria_api.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Long id) {
        super("No items found for ID: " + id);
    }

    public ItemNotFoundException(String message) {
        super(message);
    }
}



