package com.progra3.cafeteria_api.exception.user;

public class AdminAlreadyExistsException extends RuntimeException {
    public AdminAlreadyExistsException(){super("An admin already exists.");}
}
