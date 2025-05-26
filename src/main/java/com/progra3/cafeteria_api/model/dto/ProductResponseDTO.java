package com.progra3.cafeteria_api.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        Double price,
        Double cost,
        Integer stock,
        String categoryName,
        Boolean deleted,
        Boolean composite,
        List<ProductComponentResponseDTO> components,
        List<String> productGroups
) { }
