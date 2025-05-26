package com.progra3.cafeteria_api.exception;

public class NoLoggedUserException extends RuntimeException {
    public NoLoggedUserException(String message) {
        super(message);
    }
    public NoLoggedUserException(){super("No user is currently logged in");}
}
