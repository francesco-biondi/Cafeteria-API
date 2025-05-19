package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.ProductRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductResponseDTO;
import com.progra3.cafeteria_api.model.entity.Category;
import com.progra3.cafeteria_api.model.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    public ProductResponseDTO toDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .cost(product.getCost())
                .stock(product.getStock())
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .deleted(product.getDeleted())
                .isComposite(product.getIsComposite())
                .build();
    }

    public Product toEntity(ProductRequestDTO dto, Category category) {
        return Product.builder()
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .cost(dto.cost())
                .stock(dto.stock())
                .isComposite(false)
                .category(category)
                .deleted(false)
                .build();
    }

    public List<ProductResponseDTO> toDTOList(List<Product> products) {
        return products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
