package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.ItemRequestDTO;
import com.progra3.cafeteria_api.model.dto.ItemResponseDTO;
import com.progra3.cafeteria_api.model.dto.ItemTransferRequestDTO;
import com.progra3.cafeteria_api.model.entity.Item;
import com.progra3.cafeteria_api.model.entity.Order;

import java.util.List;

public interface IItemService {
    Item createItem(Order order, ItemRequestDTO itemDTO);

    Item getEntityById(Long itemId);

    Item updateItem(Long itemId, ItemRequestDTO itemDTO);

    List<ItemResponseDTO> getItemsByOrder(Long orderId);

    List<Item> transferItems(Order fromOrder, Order toOrder, List<ItemTransferRequestDTO> itemsToMove);

}
