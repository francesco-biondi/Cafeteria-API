package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.exception.ItemNotFoundException;
import com.progra3.cafeteria_api.exception.OrderNotFoundException;
import com.progra3.cafeteria_api.model.dto.ItemRequestDTO;
import com.progra3.cafeteria_api.model.dto.ItemResponseDTO;
import com.progra3.cafeteria_api.model.dto.OrderRequestDTO;
import com.progra3.cafeteria_api.model.dto.OrderResponseDTO;
import com.progra3.cafeteria_api.model.enums.OrderStatus;

import java.util.List;

public interface IOrderService {

    OrderResponseDTO create(OrderRequestDTO dto);
    OrderResponseDTO getById(Long orderId) throws OrderNotFoundException;
    List<OrderResponseDTO> getByEmployee(Long employeeId);
    List<OrderResponseDTO> getByCustomer(Long customerId);
    List<OrderResponseDTO> getByTable(Long tableId);
    List<OrderResponseDTO> getAll();
    OrderResponseDTO update(Long orderId, OrderRequestDTO dto) throws OrderNotFoundException, IllegalStateException;
    OrderResponseDTO updateDiscount(Long orderId, Integer discount) throws OrderNotFoundException, IllegalStateException;
    OrderResponseDTO updateStatus(Long orderId, OrderStatus status) throws OrderNotFoundException, IllegalStateException;

    ItemResponseDTO addItem(Long orderId, ItemRequestDTO itemDTO) throws OrderNotFoundException, IllegalStateException;
    List<ItemResponseDTO> getItems(Long orderId) throws OrderNotFoundException;
    ItemResponseDTO removeItem(Long orderId, Long itemId) throws OrderNotFoundException, IllegalStateException, ItemNotFoundException;
    ItemResponseDTO updateItem(Long orderId, Long itemId, ItemRequestDTO itemDTO) throws OrderNotFoundException, ItemNotFoundException;
}
