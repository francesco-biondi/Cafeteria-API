package com.progra3.cafeteria_api.model.dto;

public record ProductComponentResponseDTO(
        Long id,
        String productName,
        Integer quantity
) {
}
