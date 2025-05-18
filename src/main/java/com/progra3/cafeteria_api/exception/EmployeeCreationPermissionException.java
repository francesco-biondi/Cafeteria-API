package com.progra3.cafeteria_api.exception;

public class EmployeeCreationPermissionException extends RuntimeException {
    public EmployeeCreationPermissionException(String message) {
        super(message);
    }
    public EmployeeCreationPermissionException(){super("Only ADMIN can create employees.");}
}
