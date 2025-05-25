package com.progra3.cafeteria_api.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ItemResponseDTO(
        Long id,
        Long orderId,
        Long productId,
        List<SelectedProductOptionResponseDTO> selectedOptions,
        Double unitPrice,
        Integer quantity,
        String comment,
        Double totalPrice,
        Boolean deleted
){}