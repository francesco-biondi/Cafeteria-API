package com.progra3.cafeteria_api.exception;

public class OrderModificationNotAllowedException extends RuntimeException {

    public OrderModificationNotAllowedException(Long orderId, String status) {
        super("Order with ID " + orderId + " is already " + status + " and cannot be modified.");
    }

    public OrderModificationNotAllowedException(String message) {
        super(message);
    }
}
