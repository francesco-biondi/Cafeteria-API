package com.progra3.cafeteria_api.exception;

public class AuditInProgressException extends RuntimeException {
    public AuditInProgressException(String message) {
        super(message);
    }
    public AuditInProgressException(){super("An audit is currently in progress");}
}
