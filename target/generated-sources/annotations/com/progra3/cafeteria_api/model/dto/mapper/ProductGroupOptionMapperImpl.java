package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.ProductGroupOptionRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductGroupOptionResponseDTO;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import com.progra3.cafeteria_api.model.entity.ProductGroupOption;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-21T15:26:20-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class ProductGroupOptionMapperImpl implements ProductGroupOptionMapper {

    @Override
    public ProductGroupOptionResponseDTO toDTO(ProductGroupOption productGroupOption) {
        if ( productGroupOption == null ) {
            return null;
        }

        Long productId = null;
        Long id = null;
        Integer maxQuantity = null;
        Double priceIncrease = null;

        productId = productGroupOptionProductId( productGroupOption );
        id = productGroupOption.getId();
        maxQuantity = productGroupOption.getMaxQuantity();
        priceIncrease = productGroupOption.getPriceIncrease();

        ProductGroupOptionResponseDTO productGroupOptionResponseDTO = new ProductGroupOptionResponseDTO( id, productId, maxQuantity, priceIncrease );

        return productGroupOptionResponseDTO;
    }

    @Override
    public ProductGroupOption toEntity(ProductGroupOptionRequestDTO dto, ProductGroup productGroup, Product product) {
        if ( dto == null ) {
            return null;
        }

        ProductGroupOption productGroupOption = new ProductGroupOption();

        if ( dto.priceIncrease() != null ) {
            productGroupOption.setPriceIncrease( dto.priceIncrease() );
        }
        else {
            productGroupOption.setPriceIncrease( (double) 0.0 );
        }
        productGroupOption.setMaxQuantity( dto.maxQuantity() );

        assignProductGroup( productGroupOption, productGroup );
        assignProduct( productGroupOption, product );

        return productGroupOption;
    }

    private Long productGroupOptionProductId(ProductGroupOption productGroupOption) {
        if ( productGroupOption == null ) {
            return null;
        }
        Product product = productGroupOption.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
