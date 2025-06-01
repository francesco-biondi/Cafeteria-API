package com.progra3.cafeteria_api.model.mapper;

import com.progra3.cafeteria_api.model.dto.ItemRequestDTO;
import com.progra3.cafeteria_api.model.dto.ItemResponseDTO;
import com.progra3.cafeteria_api.model.entity.Item;
import com.progra3.cafeteria_api.model.entity.Order;
import com.progra3.cafeteria_api.model.entity.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {SelectedProductOptionMapper.class})
public interface ItemMapper {
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "orderId", source = "order.id")
    ItemResponseDTO toDTO(Item item);

    @Mapping(target = "unitPrice", expression = "java(item.getProduct().getPrice())")
    @Mapping(target = "totalPrice", expression = "java(item.getUnitPrice() * item.getQuantity())")
    Item toEntity(ItemRequestDTO dto, @Context Product product, @Context Order order);

    @BeforeMapping
    default void assignProduct(@MappingTarget Item item, @Context Product product) {
        item.setProduct(product);
    }

    @BeforeMapping
    default void assignOrder(@MappingTarget Item item, @Context Order order) {
        item.setOrder(order);
    }
}
