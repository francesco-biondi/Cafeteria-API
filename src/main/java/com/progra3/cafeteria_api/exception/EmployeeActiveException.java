package com.progra3.cafeteria_api.exception;

public class EmployeeActiveException extends RuntimeException {
    public EmployeeActiveException(String dni){super("Employee DNI: " + dni + " is already active");}
}
