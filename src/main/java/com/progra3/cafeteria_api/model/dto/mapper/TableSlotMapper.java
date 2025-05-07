package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.TableSlotResponseDTO;
import com.progra3.cafeteria_api.model.entity.TableSlot;
import org.springframework.stereotype.Component;

@Component
public class TableSlotMapper {
    public TableSlotResponseDTO toDTO(TableSlot tableSlot) {
        return TableSlotResponseDTO.builder()
                .id(tableSlot.getId())
                .name(tableSlot.getName())
                .status(tableSlot.getStatus().getName())
                .build();
    }
}
