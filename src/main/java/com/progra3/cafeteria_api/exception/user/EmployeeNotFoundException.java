package com.progra3.cafeteria_api.exception.user;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String email) {
        super("Employee not found by email: " + email);
    }
    public EmployeeNotFoundException(Long id){super("No employee found for ID: " + id);}
}
