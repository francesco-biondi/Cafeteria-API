package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.ItemNotFoundException;
import com.progra3.cafeteria_api.model.dto.ItemRequestDTO;
import com.progra3.cafeteria_api.model.dto.ItemResponseDTO;
import com.progra3.cafeteria_api.model.dto.mapper.ItemMapper;
import com.progra3.cafeteria_api.model.entity.Item;
import com.progra3.cafeteria_api.model.entity.Order;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemService implements IItemService {
    private final ItemMapper itemMapper;
    private final ProductService productService;

    @Override
    public Item createItem(Order order, ItemRequestDTO itemDTO) {
        Product product = productService.getEntityById(itemDTO.productId());

        return itemMapper.toEntity(itemDTO, product, order);
    }

    @Override
    public Item getItemById(Order order, Long itemId) {
        return order.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }

    //TODO ver si se puede mejorar con mapper
    @Override
    public Item updateItem(Order order, ItemRequestDTO itemDTO) {
        Item itemToUpdate = getItemById(order, itemDTO.id());

        itemToUpdate.setComment(itemDTO.comment());
        itemToUpdate.setQuantity(itemDTO.quantity());
        itemToUpdate.setTotalPrice(itemToUpdate.getUnitPrice() * itemToUpdate.getQuantity());

        return itemToUpdate;
    }

    @Override
    public List<ItemResponseDTO> getItemsByOrder(Order order) {
        return order.getItems()
                .stream()
                .map(itemMapper::toDTO)
                .toList();
    }

    @Override
    public List<Item> transferItems(Order fromOrder, Order toOrder, Map<Long, Integer> itemsToMove) {
        return itemsToMove.entrySet().stream()
                .map(entry -> {
                    Long itemId = entry.getKey();
                    int quantityToMove = entry.getValue();

                    Item originalItem = fromOrder.getItems().stream()
                            .filter(i -> i.getId().equals(itemId) && !i.getDeleted())
                            .findFirst()
                            .orElseThrow(() -> new ItemNotFoundException(itemId));

                    if (quantityToMove <= 0 || quantityToMove > originalItem.getQuantity()) {
                        throw new IllegalArgumentException("Invalid quantity for item ID: " + itemId);
                    }

                    if (quantityToMove == originalItem.getQuantity()) {
                        fromOrder.getItems().remove(originalItem);
                        originalItem.setOrder(toOrder);
                        return originalItem;
                    } else {
                        originalItem.setQuantity(originalItem.getQuantity() - quantityToMove);
                        return Item.builder()
                                .product(originalItem.getProduct())
                                .order(toOrder)
                                .comment(originalItem.getComment())
                                .unitPrice(originalItem.getUnitPrice())
                                .quantity(quantityToMove)
                                .totalPrice(originalItem.getUnitPrice() * quantityToMove)
                                .deleted(false)
                                .build();
                    }
                })
                .toList();
    }
    
}