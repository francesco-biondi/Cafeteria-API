package com.progra3.cafeteria_api.exception;

public class CustomerActiveException extends RuntimeException {
    public CustomerActiveException(String dni) {
        super("Customer DNI: " + dni + " is already active");
    }
}
