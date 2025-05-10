package com.progra3.cafeteria_api.exception;


public class SupplierNotFoundException extends RuntimeException {
  public SupplierNotFoundException(String message) {
    super(message);
  }

  public SupplierNotFoundException(Long id) {
    super("No supplier found for ID: " + id);
  }
}
