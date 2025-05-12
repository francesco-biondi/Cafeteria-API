package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.exception.ItemNotFoundException;
import com.progra3.cafeteria_api.exception.OrderModificationNotAllowedException;
import com.progra3.cafeteria_api.exception.OrderNotFoundException;
import com.progra3.cafeteria_api.model.dto.ItemRequestDTO;
import com.progra3.cafeteria_api.model.dto.ItemResponseDTO;
import com.progra3.cafeteria_api.model.dto.OrderRequestDTO;
import com.progra3.cafeteria_api.model.dto.OrderResponseDTO;
import com.progra3.cafeteria_api.model.entity.Order;
import com.progra3.cafeteria_api.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IOrderService {

    OrderResponseDTO create(OrderRequestDTO dto);
    OrderResponseDTO getById(Long orderId) throws OrderNotFoundException;
    List<OrderResponseDTO> getByEmployee(Long employeeId);
    List<OrderResponseDTO> getByCustomer(Long customerId);
    List<OrderResponseDTO> getBySeating(Long seatingId);
    List<OrderResponseDTO> getAll();
    List<Order> getByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    OrderResponseDTO update(Long orderId, OrderRequestDTO dto);
    OrderResponseDTO updateDiscount(Long orderId, Integer discount);
    OrderResponseDTO updateStatus(Long orderId, OrderStatus status);

    List<OrderResponseDTO> splitOrder(Long originalOrderId, OrderRequestDTO dto, List<ItemRequestDTO> itemsToMove);

    ItemResponseDTO addItem(Long orderId, ItemRequestDTO itemDTO);
    List<ItemResponseDTO> getItems(Long orderId);
    ItemResponseDTO removeItem(Long orderId, Long itemId);
    ItemResponseDTO updateItem(Long orderId, Long itemId, ItemRequestDTO itemDTO);
}
