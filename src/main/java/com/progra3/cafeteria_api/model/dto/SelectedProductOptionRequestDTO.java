package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SelectedProductOptionRequestDTO(
        @NotNull(message = "Option ID cannot be null")
        Long productOptionId,

        @NotNull(message = "Quantity cannot be null")
        @Positive(message = "Quantity must be positive")
        Integer quantity
) {
}
