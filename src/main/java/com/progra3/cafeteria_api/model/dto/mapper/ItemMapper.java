package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.ItemRequestDTO;
import com.progra3.cafeteria_api.model.dto.ItemResponseDTO;
import com.progra3.cafeteria_api.model.entity.Item;
import com.progra3.cafeteria_api.model.entity.Order;
import com.progra3.cafeteria_api.model.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public ItemResponseDTO toDTO(Item item) {
        return ItemResponseDTO.builder()
                .id(item.getId())
                .orderId(item.getOrder().getId())
                .productId(item.getProduct().getId())
                .unitPrice(item.getUnitPrice())
                .quantity(item.getQuantity())
                .totalPrice(item.getTotalPrice())
                .build();
    }

    public Item toEntity(ItemRequestDTO dto, Product product, Order order) {
        double unitPrice = product.getPrice();
        int quantity = dto.quantity();
        double totalPrice = unitPrice * quantity;

        return Item.builder()
                .product(product)
                .order(order)
                .comment(dto.comment())
                .unitPrice(unitPrice)
                .quantity(quantity)
                .totalPrice(totalPrice)
                .build();
    }
}
