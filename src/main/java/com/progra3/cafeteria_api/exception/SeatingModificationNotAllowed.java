package com.progra3.cafeteria_api.exception;

public class SeatingModificationNotAllowed extends RuntimeException {
  public SeatingModificationNotAllowed(String message) {
    super(message);
  }
}
