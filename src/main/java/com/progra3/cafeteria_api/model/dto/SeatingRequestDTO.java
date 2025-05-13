package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record SeatingRequestDTO(
        @NotNull(message = "Number cannot be null")
        @PositiveOrZero(message = "Number must be positive or zero")
        Integer number
){}
