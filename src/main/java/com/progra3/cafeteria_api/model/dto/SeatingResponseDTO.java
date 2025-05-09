package com.progra3.cafeteria_api.model.dto;

import lombok.Builder;

@Builder
public record SeatingResponseDTO(
        Long id,
        Integer number,
        String status
){}
