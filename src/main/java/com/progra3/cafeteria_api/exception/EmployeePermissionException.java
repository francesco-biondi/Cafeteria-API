package com.progra3.cafeteria_api.exception;

public class EmployeePermissionException extends RuntimeException {
    public EmployeePermissionException(String message) {
        super(message);
    }
    public EmployeePermissionException(){super("Only ADMIN has permission to execute this action.");}
}
