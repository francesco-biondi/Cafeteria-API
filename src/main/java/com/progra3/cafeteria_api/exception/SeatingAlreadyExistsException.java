package com.progra3.cafeteria_api.exception;

public class SeatingAlreadyExistsException extends RuntimeException {
    public SeatingAlreadyExistsException(Integer number) {
        super("Seating with number " + number + " already exists.");
    }
}
