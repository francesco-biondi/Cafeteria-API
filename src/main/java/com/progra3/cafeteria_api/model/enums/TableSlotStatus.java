package com.progra3.cafeteria_api.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TableSlotStatus {
    FREE("Free"),
    OCCUPIED("Occupied"),
    BILLING("Billing");

    private final String name;
}
