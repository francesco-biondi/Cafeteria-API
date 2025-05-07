package com.progra3.cafeteria_api.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderRequestDTO {
    private Long employeeId;
    private Long customerId;
    private Long tableId;
    private Double discount;
    private Integer peopleCount;
}
