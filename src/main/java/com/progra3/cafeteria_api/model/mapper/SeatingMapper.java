package com.progra3.cafeteria_api.model.mapper;

import com.progra3.cafeteria_api.model.dto.SeatingRequestDTO;
import com.progra3.cafeteria_api.model.dto.SeatingResponseDTO;
import com.progra3.cafeteria_api.model.entity.Seating;
import com.progra3.cafeteria_api.model.enums.SeatingStatus;
import org.springframework.stereotype.Component;

@Component
public class SeatingMapper {
    public SeatingResponseDTO toDTO(Seating seating) {
        return SeatingResponseDTO.builder()
                .id(seating.getId())
                .number(seating.getNumber())
                .status(seating.getStatus())
                .deleted(seating.getDeleted())
                .build();
    }

    public Seating toEntity(SeatingRequestDTO dto) {
        return Seating.builder()
                .number(dto.number())
                .status(SeatingStatus.FREE)
                .deleted(false)
                .build();
    }
}
