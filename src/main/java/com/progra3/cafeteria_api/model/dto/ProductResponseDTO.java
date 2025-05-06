package com.progra3.cafeteria_api.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double cost;
    private Integer stock;
    private String categoryName;
}
