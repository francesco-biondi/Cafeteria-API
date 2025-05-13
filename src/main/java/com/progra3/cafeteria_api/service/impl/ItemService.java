package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.ItemNotFoundException;
import com.progra3.cafeteria_api.model.dto.ItemRequestDTO;
import com.progra3.cafeteria_api.model.dto.ItemResponseDTO;
import com.progra3.cafeteria_api.model.dto.mapper.ItemMapper;
import com.progra3.cafeteria_api.model.entity.Item;
import com.progra3.cafeteria_api.model.entity.Order;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.repository.ItemRepository;
import com.progra3.cafeteria_api.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService implements IItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final ProductService productService;

    @Override
    public Item createItem(Order order, ItemRequestDTO itemDTO) {
        Product product = productService.getEntityById(itemDTO.productId());

        return itemMapper.toEntity(itemDTO, product, order);
    }

    @Override
    public Item getEntityById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }

    @Override
    public Item updateItem(Long itemId, ItemRequestDTO itemDTO) {
        Item itemToUpdate = getEntityById(itemId);

        itemToUpdate.setComment(itemDTO.comment());
        itemToUpdate.setQuantity(itemDTO.quantity());
        itemToUpdate.setTotalPrice(itemToUpdate.getUnitPrice() * itemToUpdate.getQuantity());

        return itemToUpdate;
    }

    @Override
    public List<ItemResponseDTO> getItemsByOrder(Long orderId) {
        return itemRepository.findByOrderId(orderId)
                .orElse(List.of())
                .stream()
                .map(itemMapper::toDTO)
                .toList();
    }

    @Override
    public List<Item> transferItems(Order fromOrder, Order toOrder, List<ItemRequestDTO> itemsToMove) {
        return itemsToMove.stream()
                .map(dto -> transferItem(fromOrder, toOrder, dto))
                .toList();
    }

    private void validateTransferQuantity(int quantityToMove, int availableQuantity) {
        if (quantityToMove < 1 || quantityToMove > availableQuantity) {
            throw new IllegalArgumentException("Transfer quantity must be between 1 and " + availableQuantity);
        }
    }

    private Item transferItem(Order fromOrder, Order toOrder, ItemRequestDTO dto) {
        int quantityToMove = dto.quantity();

        Item originalItem = itemRepository.findByOrderIdAndProductId(fromOrder.getId(), dto.productId())
                .orElseThrow(() -> new ItemNotFoundException("Item not found in the order " + fromOrder.getId() + " for product ID: " + dto.productId()));
        validateTransferQuantity(quantityToMove, originalItem.getQuantity());

        if (quantityToMove == originalItem.getQuantity()) {
            fromOrder.getItems().remove(originalItem);
            originalItem.setOrder(toOrder);
            return originalItem;
        }

        originalItem.setQuantity(originalItem.getQuantity() - quantityToMove);

        return Item.builder()
                .product(originalItem.getProduct())
                .order(toOrder)
                .comment(originalItem.getComment())
                .unitPrice(originalItem.getUnitPrice())
                .quantity(quantityToMove)
                .totalPrice(originalItem.getUnitPrice() * quantityToMove)
                .build();
    }



}