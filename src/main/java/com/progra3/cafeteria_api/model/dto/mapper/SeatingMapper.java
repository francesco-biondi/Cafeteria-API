package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.SeatingResponseDTO;
import com.progra3.cafeteria_api.model.entity.Seating;
import org.springframework.stereotype.Component;

@Component
public class SeatingMapper {
    public SeatingResponseDTO toDTO(Seating seating) {
        return SeatingResponseDTO.builder()
                .id(seating.getId())
                .number(seating.getNumber())
                .status(seating.getStatus().getName())
                .build();
    }
}
