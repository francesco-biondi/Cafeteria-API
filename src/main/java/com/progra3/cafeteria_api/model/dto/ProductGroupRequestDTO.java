package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductGroupRequestDTO(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotNull(message = "Min quantity is required")
        @PositiveOrZero(message = "Min quantity must be zero or positive")
        Integer minQuantity,

        @NotNull(message = "Max quantity is required")
        @PositiveOrZero(message = "Max quantity must be zero or positive")
        Integer maxQuantity
) {
}
