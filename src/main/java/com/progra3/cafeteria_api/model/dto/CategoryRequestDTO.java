package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
public record CategoryRequestDTO(
        @NotNull
        @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
        String name
) { }
