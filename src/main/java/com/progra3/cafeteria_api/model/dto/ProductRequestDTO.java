package com.progra3.cafeteria_api.model.dto;

import com.progra3.cafeteria_api.validation.ValidProductRequest;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
@ValidProductRequest
public record ProductRequestDTO(
        @NotNull(message = "Category id cannot be null")
        Long categoryId,

        @NotNull(message = "Name cannot be null")
        @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
        String name,

        @Size(min = 1, max = 255, message = "Description must be between 1 and 255 characters")
        String description,

        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be positive")
        Double price,

        @Positive(message = "Cost must be positive")
        Double cost,

        @NotNull(message = "Control stock cannot be null")
        Boolean controlStock,

        @PositiveOrZero(message = "Stock can't be less than 0")
        Integer stock
) { }
