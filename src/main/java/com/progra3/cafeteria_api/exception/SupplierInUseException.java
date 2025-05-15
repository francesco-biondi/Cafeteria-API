package com.progra3.cafeteria_api.exception;

public class SupplierInUseException extends RuntimeException {
    public SupplierInUseException(String message) {
        super(message);
    }
    public SupplierInUseException(Long id){
        super("Supplier ID: " + id + " is associated with existing expenses");
    }
}
