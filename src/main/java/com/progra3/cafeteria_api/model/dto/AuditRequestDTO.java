package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Builder
public record AuditRequestDTO(
        @NotNull(message = "Initial cash cannot be null")
        @PositiveOrZero(message = "Initial cash cannot be negative")
        Double initialCash
) {
}