package com.progra3.cafeteria_api.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CategoryResponseDTO(
        Long id,
        String name,
        List<ProductResponseDTO> products
) { }
