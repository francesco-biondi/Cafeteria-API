package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.ProductOptionRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductOptionResponseDTO;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import com.progra3.cafeteria_api.model.entity.ProductOption;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-27T10:54:48-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class ProductOptionMapperImpl implements ProductOptionMapper {

    @Override
    public ProductOptionResponseDTO toDTO(ProductOption productOption) {
        if ( productOption == null ) {
            return null;
        }

        Long productId = null;
        Long id = null;
        Integer maxQuantity = null;
        Double priceIncrease = null;

        productId = productOptionProductId( productOption );
        id = productOption.getId();
        maxQuantity = productOption.getMaxQuantity();
        priceIncrease = productOption.getPriceIncrease();

        ProductOptionResponseDTO productOptionResponseDTO = new ProductOptionResponseDTO( id, productId, maxQuantity, priceIncrease );

        return productOptionResponseDTO;
    }

    @Override
    public ProductOption toEntity(ProductOptionRequestDTO dto, ProductGroup productGroup, Product product) {
        if ( dto == null ) {
            return null;
        }

        ProductOption productOption = new ProductOption();

        assignProductGroup( productOption, productGroup );
        assignProduct( productOption, product );

        if ( dto.priceIncrease() != null ) {
            productOption.setPriceIncrease( dto.priceIncrease() );
        }
        else {
            productOption.setPriceIncrease( (double) 0.0 );
        }
        productOption.setMaxQuantity( dto.maxQuantity() );

        return productOption;
    }

    private Long productOptionProductId(ProductOption productOption) {
        if ( productOption == null ) {
            return null;
        }
        Product product = productOption.getProduct();
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
