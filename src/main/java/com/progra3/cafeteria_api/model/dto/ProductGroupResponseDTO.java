package com.progra3.cafeteria_api.model.dto;

import java.util.List;

public record ProductGroupResponseDTO(
        Long id,
        String name,
        Integer minQuantity,
        Integer maxQuantity,
        List<ProductGroupOptionResponseDTO> options
) {
}
