package com.progra3.cafeteria_api.exception;

public class TableNotFoundException extends RuntimeException {
    public TableNotFoundException(Long id) {
        super("No table slots found for ID: " + id);
    }

    public TableNotFoundException(String name) {
        super("No table slots found for name: " + name);
    }
}
