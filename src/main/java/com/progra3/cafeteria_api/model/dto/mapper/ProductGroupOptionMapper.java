package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.ProductGroupOptionRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductGroupOptionResponseDTO;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import com.progra3.cafeteria_api.model.entity.ProductGroupOption;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ProductGroupOptionMapper {

    @Mapping(source = "product.id", target = "productId")
    ProductGroupOptionResponseDTO toDTO(ProductGroupOption productGroupOption);

    @Mapping(target = "priceIncrease", defaultValue = "0.0")
    ProductGroupOption toEntity(ProductGroupOptionRequestDTO dto, @Context ProductGroup productGroup, @Context Product product);

    @AfterMapping
    default void assignProductGroup(@MappingTarget ProductGroupOption productGroupOption, @Context ProductGroup productGroup) {
        productGroupOption.setProductGroup(productGroup);
    }

    @AfterMapping
    default void assignProduct(@MappingTarget ProductGroupOption productGroupOption, @Context Product product) {
        productGroupOption.setProduct(product);
    }

}
