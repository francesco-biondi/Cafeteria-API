package com.progra3.cafeteria_api.model.dto;

public record ProductOptionResponseDTO(
        Long id,
        Long productId,
        Integer maxQuantity,
        Double priceIncrease
) {
}
