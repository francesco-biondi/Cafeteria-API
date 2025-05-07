package com.progra3.cafeteria_api.model.dto;

import lombok.Builder;

@Builder
public record TableSlotResponseDTO (
    Long id,
    String name,
    String status
){}
