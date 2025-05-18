package com.progra3.cafeteria_api.exception;

public class AdminExistsException extends RuntimeException {
    public AdminExistsException(String message) {
        super(message);
    }
    public AdminExistsException(){super("An admin already exists.");}
}
