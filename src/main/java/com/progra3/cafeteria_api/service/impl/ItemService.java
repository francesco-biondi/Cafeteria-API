package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.ItemNotFoundException;
import com.progra3.cafeteria_api.model.dto.ItemRequestDTO;
import com.progra3.cafeteria_api.model.dto.ItemResponseDTO;
import com.progra3.cafeteria_api.model.dto.mapper.ItemMapper;
import com.progra3.cafeteria_api.model.entity.Item;
import com.progra3.cafeteria_api.model.entity.Order;
import com.progra3.cafeteria_api.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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


    @Override
    public Item updateItem(Order order, Long itemId, ItemRequestDTO itemDTO) {
        Item itemToUpdate = getItemById(order, itemId);

        itemToUpdate.setComment(itemDTO.comment());
        itemToUpdate.setQuantity(itemDTO.quantity());
        itemToUpdate.setTotalPrice(itemToUpdate.getUnitPrice() * itemDTO.quantity());

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
    public List<Item> splitItemsFromOrder(Order originalOrder, List<ItemRequestDTO> itemsToMove, Order newOrder) {

        return itemsToMove.stream()
                .map(dto -> {
                    Product product = productService.getEntityById(dto.productId());

                    Item originalItem = originalOrder.getItems().stream()
                            .filter(item -> item.getProduct().getId().equals(dto.productId()) && !item.getDeleted())
                            .findFirst()
                            .orElseThrow(() -> new ItemNotFoundException("No items found for product ID: ", dto.productId()));

                    int originalQty = originalItem.getQuantity();
                    int qtyToMove = dto.quantity();

                    if (qtyToMove <= 0 || qtyToMove > originalQty) {
                        throw new IllegalArgumentException("Invalid quantity for product ID: " + dto.productId());
                    }

                    if (qtyToMove == originalQty) {
                        originalOrder.getItems().remove(originalItem);
                        originalItem.setOrder(newOrder);
                        return originalItem;
                    }

                    originalItem.setQuantity(originalQty - qtyToMove);

                    return itemMapper.toEntity(dto, product, newOrder);
                })
                .toList();
    }
}