package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.exception.ItemNotFoundException;
import com.progra3.cafeteria_api.exception.OrderModificationNotAllowedException;
import com.progra3.cafeteria_api.exception.OrderNotFoundException;
import com.progra3.cafeteria_api.model.dto.ItemRequestDTO;
import com.progra3.cafeteria_api.model.dto.ItemResponseDTO;
import com.progra3.cafeteria_api.model.dto.OrderRequestDTO;
import com.progra3.cafeteria_api.model.dto.OrderResponseDTO;
import com.progra3.cafeteria_api.model.enums.OrderStatus;

import java.util.List;
import java.util.Map;

public interface IOrderService {

    OrderResponseDTO create(OrderRequestDTO dto);
    OrderResponseDTO getById(Long orderId) throws OrderNotFoundException;
    List<OrderResponseDTO> getByEmployee(Long employeeId);
    List<OrderResponseDTO> getByCustomer(Long customerId);
    List<OrderResponseDTO> getBySeating(Long seatingId);
    List<OrderResponseDTO> getAll();
    OrderResponseDTO update(Long orderId, OrderRequestDTO dto) throws OrderNotFoundException, OrderModificationNotAllowedException;
    OrderResponseDTO updateDiscount(Long orderId, Integer discount) throws OrderNotFoundException, OrderModificationNotAllowedException;
    OrderResponseDTO updateStatus(Long orderId, OrderStatus status) throws OrderNotFoundException, OrderModificationNotAllowedException;

    public List<OrderResponseDTO> splitOrder(Long originalOrderId, OrderRequestDTO dto) throws OrderNotFoundException, OrderModificationNotAllowedException;

    ItemResponseDTO addItem(Long orderId, ItemRequestDTO itemDTO) throws OrderNotFoundException, OrderModificationNotAllowedException;
    List<ItemResponseDTO> getItems(Long orderId) throws OrderNotFoundException;
    ItemResponseDTO removeItem(Long orderId, Long itemId) throws OrderNotFoundException, ItemNotFoundException, OrderModificationNotAllowedException;
    ItemResponseDTO updateItem(Long orderId, Long itemId, ItemRequestDTO itemDTO) throws OrderNotFoundException, ItemNotFoundException, OrderModificationNotAllowedException;
}
