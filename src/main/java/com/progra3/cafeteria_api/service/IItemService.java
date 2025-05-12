package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.ItemRequestDTO;
import com.progra3.cafeteria_api.model.dto.ItemResponseDTO;
import com.progra3.cafeteria_api.model.entity.Item;
import com.progra3.cafeteria_api.model.entity.Order;

import java.util.List;
import java.util.Map;

public interface IItemService {
    Item createItem(Order order, ItemRequestDTO itemDTO);

    Item getItemById(Order order, Long itemId);

    Item updateItem(Order order, ItemRequestDTO itemDTO);

    List<ItemResponseDTO> getItemsByOrder(Order order);

    List<Item> transferItems(Order fromOrder, Order toOrder, List<ItemRequestDTO> itemsToMove);

}
