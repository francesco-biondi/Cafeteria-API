package com.progra3.cafeteria_api.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    IN_PROGRESS("In Progress"),
    FINALIZED("Finalized"),
    CANCELED("Canceled");

    private final String name;
}
