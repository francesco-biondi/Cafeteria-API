package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductGroupOptionRequestDTO(

        @NotNull(message = "Product group ID is required")
        Long productId,

        @NotNull(message = "Max quantity is required")
        @Positive(message = "Max quantity must be positive")
        Integer maxQuantity,

        @PositiveOrZero(message = "Price increase must be positive or zero")
        Double priceIncrease
) {
}
