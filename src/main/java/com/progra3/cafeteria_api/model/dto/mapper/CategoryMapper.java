package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.CategoryRequestDTO;
import com.progra3.cafeteria_api.model.dto.CategoryResponseDTO;
import com.progra3.cafeteria_api.model.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final ProductMapper productMapper;

    public CategoryResponseDTO toDTO(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .products(Optional.ofNullable(category.getProducts())
                        .map(productMapper::toDTOList)
                        .orElse(List.of()))
                .build();
    }

    public Category toEntity(CategoryRequestDTO dto) {
        return Category.builder()
                .name(dto.name())
                .build();
    }
}
