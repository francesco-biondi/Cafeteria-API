package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ProductRequestDTO(
        @NotNull(message = "El nombre no puede ser nulo")
        @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
        String name,

        @Size(min = 1, max = 255, message = "La descripción debe tener entre 1 y 255 caracteres")
        String description,

        @NotNull(message = "El precio no puede ser nulo")
        @Positive(message = "El precio debe ser un valor positivo")
        Double price,

        @Positive(message = "El costo debe ser un valor positivo")
        Double cost,

        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock,

        @NotNull(message = "El ID de la categoría no puede ser nulo")
        Long categoryId
) { }
