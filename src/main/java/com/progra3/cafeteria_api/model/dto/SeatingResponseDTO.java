package com.progra3.cafeteria_api.model.dto;

import com.progra3.cafeteria_api.model.enums.SeatingStatus;
import lombok.Builder;

@Builder
public record SeatingResponseDTO(
        Long id,
        Integer number,
        SeatingStatus status,
        Boolean deleted
){}
