package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record TableSlotRequestDTO (
        @NotNull(message = "Name cannot be null")
        @NotBlank(message = "Name cannot be blank")
        String name
){}
