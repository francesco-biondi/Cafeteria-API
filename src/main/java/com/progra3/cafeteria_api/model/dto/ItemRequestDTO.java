package com.progra3.cafeteria_api.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ItemRequestDTO {
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private String comment;
}