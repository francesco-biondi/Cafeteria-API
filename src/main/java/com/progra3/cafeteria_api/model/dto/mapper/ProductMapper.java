package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.ProductRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductResponseDTO;
import com.progra3.cafeteria_api.model.entity.Category;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {ProductComponentMapper.class})
public interface ProductMapper {

    @Mapping(source = "category.name", target = "categoryName")
    ProductResponseDTO toDTO(Product product);

    List<ProductResponseDTO> toDTOList(List<Product> products);

    Product toEntity(ProductRequestDTO dto, @Context Category category);

    @BeforeMapping
    default void assignCategory(@MappingTarget Product product, @Context Category category) {
        product.setCategory(category);
    }

    default List<String> map(List<ProductGroup> groups) {
        return groups.stream()
                .map(ProductGroup::getName)
                .toList();
    }
}