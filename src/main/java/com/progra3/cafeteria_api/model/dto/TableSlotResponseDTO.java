package com.progra3.cafeteria_api.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TableSlotResponseDTO {
    private Long id;
    private String name;
    private String status;
}
