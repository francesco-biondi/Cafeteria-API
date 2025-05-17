package com.progra3.cafeteria_api.model.dto;

import com.progra3.cafeteria_api.model.enums.ProductComponentType;
import lombok.Builder;

@Builder
public record ProductComponentDTO(
        ProductComponentType type,
        Long referenceId,
        Integer quantity
) {}
