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

@Service
@RequiredArgsConstructor
public class ItemService implements IItemService {
    private final ItemMapper itemMapper;
    private final ProductService productService;

    @Override
    public Item createItem(Order order, ItemRequestDTO itemDTO) {
        Product product = productService.findEntityById(itemDTO.getProductId());

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

        itemToUpdate.setComment(itemDTO.getComment());
        itemToUpdate.setQuantity(itemDTO.getQuantity());

        return itemToUpdate;
    }

    @Override
    public List<ItemResponseDTO> getItemsByOrder(Order order) {
        return order.getItems()
                .stream()
                .map(itemMapper::toDTO)
                .toList();
    }
}