package com.progra3.cafeteria_api.model.mapper;

import com.progra3.cafeteria_api.model.dto.ProductOptionRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductOptionResponseDTO;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import com.progra3.cafeteria_api.model.entity.ProductOption;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductOptionMapper {

    @Mapping(source = "product.id", target = "productId")
    ProductOptionResponseDTO toDTO(ProductOption productOption);

    ProductOption toEntity(ProductOptionRequestDTO dto, @Context ProductGroup productGroup, @Context Product product);

    ProductOption updateProductOptionFromDTO(@MappingTarget ProductOption productOption, ProductOptionRequestDTO dto);

    @BeforeMapping
    default void assignProductGroup(@MappingTarget ProductOption productOption, @Context ProductGroup productGroup) {
        productOption.setProductGroup(productGroup);
    }

    @BeforeMapping
    default void assignProduct(@MappingTarget ProductOption productOption, @Context Product product) {
        productOption.setProduct(product);
    }

}
