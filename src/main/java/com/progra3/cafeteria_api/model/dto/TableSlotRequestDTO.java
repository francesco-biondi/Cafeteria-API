package com.progra3.cafeteria_api.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TableSlotRequestDTO {
    private String name;
}
