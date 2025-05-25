package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.ProductComponentRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductComponentResponseDTO;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductComponent;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-25T00:32:09-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class ProductComponentMapperImpl implements ProductComponentMapper {

    @Override
    public ProductComponentResponseDTO toDTO(ProductComponent productComponent) {
        if ( productComponent == null ) {
            return null;
        }

        String productName = null;
        Long id = null;
        Integer quantity = null;

        productName = productComponentProductName( productComponent );
        id = productComponent.getId();
        quantity = productComponent.getQuantity();

        ProductComponentResponseDTO productComponentResponseDTO = new ProductComponentResponseDTO( id, productName, quantity );

        return productComponentResponseDTO;
    }

    @Override
    public ProductComponent toEntity(ProductComponentRequestDTO dto, Product product) {
        if ( dto == null ) {
            return null;
        }

        ProductComponent productComponent = new ProductComponent();

        setProduct( productComponent, product );

        productComponent.setQuantity( dto.quantity() );

        return productComponent;
    }

    private String productComponentProductName(ProductComponent productComponent) {
        if ( productComponent == null ) {
            return null;
        }
        Product product = productComponent.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
