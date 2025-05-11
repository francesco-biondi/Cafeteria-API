package com.progra3.cafeteria_api.exception;

public class AuditNotFoundException extends RuntimeException {
    public AuditNotFoundException(String message) {
        super(message);
    }
    public AuditNotFoundException(Long id) {
    super("No audit found for ID: " + id);
  }
}
