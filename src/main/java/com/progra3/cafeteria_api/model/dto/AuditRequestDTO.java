package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;


import java.time.LocalDateTime;

@Builder
public record AuditRequestDTO(
        Long id,

        @NotBlank(message = "Initial cash cannot be null")
        @PositiveOrZero(message = "Initial cash cannot be negative")
        Double initialCash
) {
}