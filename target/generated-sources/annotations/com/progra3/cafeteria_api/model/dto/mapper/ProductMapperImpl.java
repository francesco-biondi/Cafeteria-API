package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.ProductComponentDTO;
import com.progra3.cafeteria_api.model.dto.ProductGroupResponseDTO;
import com.progra3.cafeteria_api.model.dto.ProductRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductResponseDTO;
import com.progra3.cafeteria_api.model.entity.Category;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductComponent;
import com.progra3.cafeteria_api.model.enums.ProductComponentType;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-23T15:23:48-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Autowired
    private ProductGroupMapper productGroupMapper;

    @Override
    public ProductResponseDTO toDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        String categoryName = null;
        Long id = null;
        String name = null;
        String description = null;
        Double price = null;
        Double cost = null;
        Integer stock = null;
        Boolean deleted = null;
        Boolean composite = null;
        List<ProductComponentDTO> components = null;
        List<ProductGroupResponseDTO> productGroups = null;

        categoryName = productCategoryName( product );
        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        price = product.getPrice();
        cost = product.getCost();
        stock = product.getStock();
        deleted = product.getDeleted();
        composite = product.getComposite();
        components = productComponentListToProductComponentDTOList( product.getComponents() );
        productGroups = productGroupMapper.toDTOList( product.getProductGroups() );

        ProductResponseDTO productResponseDTO = new ProductResponseDTO( id, name, description, price, cost, stock, categoryName, deleted, composite, components, productGroups );

        return productResponseDTO;
    }

    @Override
    public List<ProductResponseDTO> toDTOList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductResponseDTO> list = new ArrayList<ProductResponseDTO>( products.size() );
        for ( Product product : products ) {
            list.add( toDTO( product ) );
        }

        return list;
    }

    @Override
    public Product toEntity(ProductRequestDTO dto, Category category) {
        if ( dto == null ) {
            return null;
        }

        Product product = new Product();

        product.setName( dto.name() );
        product.setDescription( dto.description() );
        product.setPrice( dto.price() );
        product.setCost( dto.cost() );
        product.setStock( dto.stock() );

        product.setComposite( false );
        product.setDeleted( false );

        assignCategory( product, category );

        return product;
    }

    private String productCategoryName(Product product) {
        if ( product == null ) {
            return null;
        }
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        String name = category.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected ProductComponentDTO productComponentToProductComponentDTO(ProductComponent productComponent) {
        if ( productComponent == null ) {
            return null;
        }

        Integer quantity = null;

        quantity = productComponent.getQuantity();

        ProductComponentType type = null;
        Long referenceId = null;

        ProductComponentDTO productComponentDTO = new ProductComponentDTO( type, referenceId, quantity );

        return productComponentDTO;
    }

    protected List<ProductComponentDTO> productComponentListToProductComponentDTOList(List<ProductComponent> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductComponentDTO> list1 = new ArrayList<ProductComponentDTO>( list.size() );
        for ( ProductComponent productComponent : list ) {
            list1.add( productComponentToProductComponentDTO( productComponent ) );
        }

        return list1;
    }
}
