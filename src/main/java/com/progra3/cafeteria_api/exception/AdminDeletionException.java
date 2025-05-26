package com.progra3.cafeteria_api.exception;

public class AdminDeletionException extends RuntimeException {
    public AdminDeletionException(String message) {
        super(message);
    }
    public AdminDeletionException(){super("Cannot delete ADMIN.");}
}
