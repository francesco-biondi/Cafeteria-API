package com.progra3.cafeteria_api.model.dto;

public record ProductGroupOptionResponseDTO(
        Long id,
        Long productId,
        Integer maxQuantity,
        Double priceIncrease
) {
}
