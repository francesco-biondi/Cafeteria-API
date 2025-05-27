package com.progra3.cafeteria_api.exception.user;

public class EmployeeCannotBeDeletedPermission extends RuntimeException {
    public EmployeeCannotBeDeletedPermission(){super("Only ADMIN can delete employees.");}
}
