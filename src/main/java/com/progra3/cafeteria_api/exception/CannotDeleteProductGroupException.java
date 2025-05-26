package com.progra3.cafeteria_api.exception;

public class CannotDeleteProductGroupException extends RuntimeException {
    public CannotDeleteProductGroupException(Long id) {
        super("Cannot delete product group with ID: " + id + " because it has associated products.");
    }
}
