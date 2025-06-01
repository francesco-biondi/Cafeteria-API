package com.progra3.cafeteria_api.model.mapper;

import com.progra3.cafeteria_api.model.dto.ProductOptionRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductOptionResponseDTO;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import com.progra3.cafeteria_api.model.entity.ProductOption;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ProductOptionMapper {

    @Mapping(source = "product.id", target = "productId")
    ProductOptionResponseDTO toDTO(ProductOption productOption);

    @Mapping(target = "priceIncrease", defaultValue = "0.0")
    ProductOption toEntity(ProductOptionRequestDTO dto, @Context ProductGroup productGroup, @Context Product product);

    @BeforeMapping
    default void assignProductGroup(@MappingTarget ProductOption productOption, @Context ProductGroup productGroup) {
        productOption.setProductGroup(productGroup);
    }

    @BeforeMapping
    default void assignProduct(@MappingTarget ProductOption productOption, @Context Product product) {
        productOption.setProduct(product);
    }

}
