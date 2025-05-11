package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record ItemRequestDTO(
        Long id,

        @NotNull(message = "productId cannot be null")
        Long productId,

        @NotNull
        @Min(value = 1, message = "Minimum quantity is 1")
        Integer quantity,

        String comment
) {}
