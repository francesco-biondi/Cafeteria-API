package com.progra3.cafeteria_api.exception.user;

public class AdminCannotBeDeletedException extends RuntimeException {
    public AdminCannotBeDeletedException(){super("Cannot delete ADMIN.");}
}
