package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.ProductComponentDTO;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductComponent;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import com.progra3.cafeteria_api.model.entity.components.FixedProductComponent;
import com.progra3.cafeteria_api.model.entity.components.GroupComponent;
import com.progra3.cafeteria_api.model.enums.ProductComponentType;
import org.springframework.stereotype.Component;

@Component
public class ProductComponentMapper {

    public ProductComponent toEntity(ProductComponentDTO dto, Product parentProduct, Product fixedProduct) {
        return FixedProductComponent.builder()
                .quantity(dto.quantity())
                .parentProduct(parentProduct)
                .fixedProduct(fixedProduct)
                .build();
    }

    public ProductComponent toEntity(ProductComponentDTO dto, Product parentProduct, ProductGroup group) {
        return GroupComponent.builder()
                .quantity(dto.quantity())
                .parentProduct(parentProduct)
                .group(group)
                .build();
    }

    public ProductComponentDTO toDTO(ProductComponent component) {
        if (component instanceof FixedProductComponent f) {
            return ProductComponentDTO.builder()
                    .type(ProductComponentType.FIXED)
                    .referenceId(f.getFixedProduct().getId())
                    .quantity(component.getQuantity())
                    .build();
        } else if (component instanceof GroupComponent g) {
            return ProductComponentDTO.builder()
                    .type(ProductComponentType.GROUP)
                    .referenceId(g.getGroup().getId())
                    .quantity(component.getQuantity())
                    .build();
        }else return null;
    }
}
