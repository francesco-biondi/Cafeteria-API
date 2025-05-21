package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.ProductRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductResponseDTO;
import com.progra3.cafeteria_api.model.entity.Category;
import com.progra3.cafeteria_api.model.entity.Product;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {ProductGroupMapper.class})
public interface ProductMapper {

    @Mapping(source = "category.name", target = "categoryName")
    ProductResponseDTO toDTO(Product product);

    List<ProductResponseDTO> toDTOList(List<Product> products);

    @Mapping(target = "isComposite", constant = "false")
    @Mapping(target = "deleted", constant = "false")
    Product toEntity(ProductRequestDTO dto, @Context Category category);

    @AfterMapping
    default void assignCategory(@MappingTarget Product product, @Context Category category) {
        product.setCategory(category);
    }
}