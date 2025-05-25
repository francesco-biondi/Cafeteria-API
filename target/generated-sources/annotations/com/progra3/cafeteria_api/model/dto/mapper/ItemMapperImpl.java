package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.ItemRequestDTO;
import com.progra3.cafeteria_api.model.dto.ItemResponseDTO;
import com.progra3.cafeteria_api.model.dto.SelectedProductOptionResponseDTO;
import com.progra3.cafeteria_api.model.entity.Item;
import com.progra3.cafeteria_api.model.entity.Order;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.SelectedProductOption;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-25T00:38:41-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class ItemMapperImpl implements ItemMapper {

    @Autowired
    private SelectedProductOptionMapper selectedProductOptionMapper;

    @Override
    public ItemResponseDTO toDTO(Item item) {
        if ( item == null ) {
            return null;
        }

        Long productId = null;
        Long orderId = null;
        Long id = null;
        List<SelectedProductOptionResponseDTO> selectedOptions = null;
        Double unitPrice = null;
        Integer quantity = null;
        String comment = null;
        Double totalPrice = null;
        Boolean deleted = null;

        productId = itemProductId( item );
        orderId = itemOrderId( item );
        id = item.getId();
        selectedOptions = selectedProductOptionListToSelectedProductOptionResponseDTOList( item.getSelectedOptions() );
        unitPrice = item.getUnitPrice();
        quantity = item.getQuantity();
        comment = item.getComment();
        totalPrice = item.getTotalPrice();
        deleted = item.getDeleted();

        ItemResponseDTO itemResponseDTO = new ItemResponseDTO( id, orderId, productId, selectedOptions, unitPrice, quantity, comment, totalPrice, deleted );

        return itemResponseDTO;
    }

    @Override
    public Item toEntity(ItemRequestDTO dto, Product product, Order order) {
        if ( dto == null ) {
            return null;
        }

        Item item = new Item();

        assignProduct( item, product );
        assignOrder( item, order );

        item.setSelectedOptions( selectedProductOptionMapper.toEntityList( dto.selectedOptions() ) );
        item.setComment( dto.comment() );
        item.setQuantity( dto.quantity() );

        item.setUnitPrice( item.getProduct().getPrice() );
        item.setTotalPrice( item.getUnitPrice() * item.getQuantity() );

        return item;
    }

    private Long itemProductId(Item item) {
        if ( item == null ) {
            return null;
        }
        Product product = item.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long itemOrderId(Item item) {
        if ( item == null ) {
            return null;
        }
        Order order = item.getOrder();
        if ( order == null ) {
            return null;
        }
        Long id = order.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected List<SelectedProductOptionResponseDTO> selectedProductOptionListToSelectedProductOptionResponseDTOList(List<SelectedProductOption> list) {
        if ( list == null ) {
            return null;
        }

        List<SelectedProductOptionResponseDTO> list1 = new ArrayList<SelectedProductOptionResponseDTO>( list.size() );
        for ( SelectedProductOption selectedProductOption : list ) {
            list1.add( selectedProductOptionMapper.toDTO( selectedProductOption ) );
        }

        return list1;
    }
}
