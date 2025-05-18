package com.progra3.cafeteria_api.exception;

public class EmployeeDeletionPermission extends RuntimeException {
    public EmployeeDeletionPermission(String message) {
        super(message);
    }
    public EmployeeDeletionPermission(){super("Only ADMIN can delete employees.");}
}
