package com.progra3.cafeteria_api.model.dto;

import lombok.Builder;

@Builder
public record ItemResponseDTO(
        Long id,
        Long orderId,
        String productName,
        Double unitPrice,
        Integer quantity
){}