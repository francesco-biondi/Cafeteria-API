package com.progra3.cafeteria_api.model.mapper;

import com.progra3.cafeteria_api.model.dto.ProductRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductResponseDTO;
import com.progra3.cafeteria_api.model.entity.Business;
import com.progra3.cafeteria_api.model.entity.Category;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {ProductComponentMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mapping(source = "category.name", target = "categoryName")
    ProductResponseDTO toDTO(Product product);

    List<ProductResponseDTO> toDTOList(List<Product> products);

    Product toEntity(ProductRequestDTO dto, @Context Category category, @Context Business business);

    Product updateProductFromDTO(@MappingTarget Product product, ProductRequestDTO dto, @Context Category category);

    @BeforeMapping
    default void assignCategory(@MappingTarget Product product, @Context Category category) {
        if (category != null) product.setCategory(category);
    }

    @BeforeMapping
    default void assignBusiness(@MappingTarget Product product, @Context Business business) {
        if (business != null) product.setBusiness(business);
    }

    default List<String> map(Set<ProductGroup> groups) {
        return groups.stream()
                .map(ProductGroup::getName)
                .toList();
    }
}