package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.exception.order.OrderNotFoundException;
import com.progra3.cafeteria_api.model.dto.*;
import com.progra3.cafeteria_api.model.entity.Order;
import com.progra3.cafeteria_api.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderService {

    OrderResponseDTO create(OrderRequestDTO dto);
    OrderResponseDTO getById(Long orderId) throws OrderNotFoundException;
    List<OrderResponseDTO> getByEmployee(Long employeeId);
    List<OrderResponseDTO> getByCustomer(Long customerId);
    List<OrderResponseDTO> getAll();
    List<Order> getByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    OrderResponseDTO update(Long orderId, OrderRequestDTO dto);
    OrderResponseDTO updateDiscount(Long orderId, Integer discount);
    OrderResponseDTO updateStatus(Long orderId, OrderStatus status);

    List<OrderResponseDTO> transferItemsBetweenOrders(Long originalOrderId, OrderSplitRequestDTO dto) ;

    List<ItemResponseDTO> addItems(Long orderId, List<ItemRequestDTO> itemRequestDTOList);
    List<ItemResponseDTO> getItems(Long orderId);
    ItemResponseDTO removeItem(Long orderId, Long itemId);
    ItemResponseDTO updateItem(Long orderId, Long itemId, ItemRequestDTO itemDTO);
}
