package com.progra3.cafeteria_api.model.dto;

import com.progra3.cafeteria_api.model.enums.ProductComponentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record ProductComponentDTO(

        @NotNull(message = "Type cannot be null")
        ProductComponentType type,

        @NotNull(message = "Reference id cannot be null")
        @Positive(message = "Reference id must be positive")
        Long referenceId,

        @NotNull(message = "Quantity cannot be null")
        @PositiveOrZero(message = "Quantity can't be less than 0")
        Integer quantity
) {}
