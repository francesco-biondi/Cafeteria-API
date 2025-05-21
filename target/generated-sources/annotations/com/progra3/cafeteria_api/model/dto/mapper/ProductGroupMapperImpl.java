package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.ProductGroupOptionResponseDTO;
import com.progra3.cafeteria_api.model.dto.ProductGroupRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductGroupResponseDTO;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import com.progra3.cafeteria_api.model.entity.ProductGroupOption;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-21T16:21:32-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class ProductGroupMapperImpl implements ProductGroupMapper {

    @Autowired
    private ProductGroupOptionMapper productGroupOptionMapper;

    @Override
    public ProductGroupResponseDTO toDTO(ProductGroup productGroup) {
        if ( productGroup == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        Integer minQuantity = null;
        Integer maxQuantity = null;
        List<ProductGroupOptionResponseDTO> options = null;

        id = productGroup.getId();
        name = productGroup.getName();
        minQuantity = productGroup.getMinQuantity();
        maxQuantity = productGroup.getMaxQuantity();
        options = productGroupOptionListToProductGroupOptionResponseDTOList( productGroup.getOptions() );

        ProductGroupResponseDTO productGroupResponseDTO = new ProductGroupResponseDTO( id, name, minQuantity, maxQuantity, options );

        return productGroupResponseDTO;
    }

    @Override
    public List<ProductGroupResponseDTO> toDTOList(List<ProductGroup> productGroups) {
        if ( productGroups == null ) {
            return null;
        }

        List<ProductGroupResponseDTO> list = new ArrayList<ProductGroupResponseDTO>( productGroups.size() );
        for ( ProductGroup productGroup : productGroups ) {
            list.add( toDTO( productGroup ) );
        }

        return list;
    }

    @Override
    public ProductGroup toEntity(ProductGroupRequestDTO productGroupRequestDTO) {
        if ( productGroupRequestDTO == null ) {
            return null;
        }

        ProductGroup productGroup = new ProductGroup();

        productGroup.setName( productGroupRequestDTO.name() );
        productGroup.setMinQuantity( productGroupRequestDTO.minQuantity() );
        productGroup.setMaxQuantity( productGroupRequestDTO.maxQuantity() );

        return productGroup;
    }

    protected List<ProductGroupOptionResponseDTO> productGroupOptionListToProductGroupOptionResponseDTOList(List<ProductGroupOption> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductGroupOptionResponseDTO> list1 = new ArrayList<ProductGroupOptionResponseDTO>( list.size() );
        for ( ProductGroupOption productGroupOption : list ) {
            list1.add( productGroupOptionMapper.toDTO( productGroupOption ) );
        }

        return list1;
    }
}
