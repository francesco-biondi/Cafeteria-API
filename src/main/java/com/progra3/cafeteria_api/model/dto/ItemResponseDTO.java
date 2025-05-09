package com.progra3.cafeteria_api.model.dto;

import lombok.Builder;

@Builder
public record ItemResponseDTO(
        Long id,
        Long orderId,
        Long productId,
        Double unitPrice,
        Integer quantity,
        Double totalPrice
){}