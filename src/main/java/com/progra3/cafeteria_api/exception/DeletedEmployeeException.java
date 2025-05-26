package com.progra3.cafeteria_api.exception;

public class DeletedEmployeeException extends RuntimeException {
    public DeletedEmployeeException(String message) {
        super(message);
    }
    public DeletedEmployeeException(){super("Employee is deleted.");}
}
