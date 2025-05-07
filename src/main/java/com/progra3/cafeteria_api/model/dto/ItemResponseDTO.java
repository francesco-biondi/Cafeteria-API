package com.progra3.cafeteria_api.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ItemResponseDTO {
    private Long id;
    private Long orderId;
    private String productName;
    private Double unitPrice;
    private Integer quantity;
}
