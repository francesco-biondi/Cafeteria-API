package com.progra3.cafeteria_api.exception.audit;

public class AuditNotFoundException extends RuntimeException {
    public AuditNotFoundException(Long id) {
    super("No audit found for ID " + id + " for the current business.");
  }
}
