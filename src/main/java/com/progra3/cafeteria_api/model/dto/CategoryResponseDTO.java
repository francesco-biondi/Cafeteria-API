package com.progra3.cafeteria_api.model.dto;

import com.progra3.cafeteria_api.model.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private List<ProductResponseDTO> products = new ArrayList<>();
}
