package com.progra3.cafeteria_api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Builder
public record AuditRequestDTO(
        @Schema(description = "Initial amount of cash", example = "1000.0", required = true)
        @NotNull(message = "Initial cash cannot be null")
        @PositiveOrZero(message = "Initial cash cannot be negative")
        Double initialCash
) { }