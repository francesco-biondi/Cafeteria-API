package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.OrderModificationNotAllowedException;
import com.progra3.cafeteria_api.exception.OrderNotFoundException;
import com.progra3.cafeteria_api.model.dto.*;
import com.progra3.cafeteria_api.model.dto.mapper.ItemMapper;
import com.progra3.cafeteria_api.model.dto.mapper.OrderMapper;
import com.progra3.cafeteria_api.model.entity.Item;
import com.progra3.cafeteria_api.model.entity.Order;
import com.progra3.cafeteria_api.model.entity.TableSlot;
import com.progra3.cafeteria_api.model.enums.OrderStatus;
import com.progra3.cafeteria_api.model.enums.TableSlotStatus;
import com.progra3.cafeteria_api.repository.OrderRepository;
import com.progra3.cafeteria_api.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final TableSlotService tableSlotService;
    private final ItemService itemService;

    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;

    @Override
    public OrderResponseDTO create(OrderRequestDTO dto) {
        Employee employee = employeeService.getEntityById(dto.getEmployeeId()).orElse(null);
        Customer customer = customerService.getEntityById(dto.getCustomerId()).orElse(null);
        TableSlot tableSlot = tableSlotService.getEntityById(dto.getTableId());

        Order order = orderMapper.toEntity(dto, employee, customer, tableSlot);

        return orderMapper.toDTO(orderRepository.save(order));
    }

    @Override
    public OrderResponseDTO getById(Long orderId) {
        return orderMapper.toDTO(getEntityById(orderId));
    }

    @Override
    public List<OrderResponseDTO> getByEmployee(Long employeeId) {
        return orderRepository.findByEmployeeId(employeeId)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public List<OrderResponseDTO> getByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public List<OrderResponseDTO> getByTable(Long tableId) {
        return orderRepository.findByTableId(tableId)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public List<OrderResponseDTO> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public OrderResponseDTO update(Long orderId, OrderRequestDTO dto) throws OrderNotFoundException, IllegalStateException {
        Order order = getEntityById(orderId);

        validateOrderStatus(order);

        order.setEmployee(employeeService.getEntityById(dto.getEmployeeId()).orElse(null));
        order.setCustomer(customerService.getEntityById(dto.getCustomerId()).orElse(null));
        order.setTableSlot(tableSlotService.getEntityById(dto.getTableId()));
        order.setPeopleCount(dto.getPeopleCount());

        recalculate(order);

        return orderMapper.toDTO(orderRepository.save(order));
    }

    @Override
    public OrderResponseDTO updateDiscount(Long orderId, Double discount) {
        Order order = getEntityById(orderId);

        validateOrderStatus(order);

        applyDiscount(order, discount);

        return orderMapper.toDTO(orderRepository.save(order));
    }

    @Override
    public OrderResponseDTO updateStatus(Long id, OrderStatus status){
        Order order = getEntityById(id);

        if (status.equals(OrderStatus.BILLED))
            tableSlotService.updateStatus(order.getTableSlot().getId(), TableSlotStatus.BILLING);
        else
            tableSlotService.updateStatus(order.getTableSlot().getId(), TableSlotStatus.FREE);

        validateOrderStatus(order);

        order.setStatus(status);

        return orderMapper.toDTO(orderRepository.save(order));
    }


    @Override
    public ItemResponseDTO addItem(Long orderId, ItemRequestDTO itemDTO) {
        Order order = getEntityById(orderId);

        validateOrderStatus(order);

        Item newItem = itemService.createItem(order, itemDTO);

        order.getItems().add(newItem);
        recalculate(order);

        orderRepository.save(order);

        return itemMapper.toDTO(newItem);
    }

    @Override
    public ItemResponseDTO removeItem(Long orderId, Long itemId) {
        Order order = getEntityById(orderId);

        validateOrderStatus(order);

        Item itemToRemove = itemService.getItemById(order, itemId);
        itemToRemove.setDeleted(true);

        recalculate(order);
        orderRepository.save(order);

        return itemMapper.toDTO(itemToRemove);
    }

    @Override
    public ItemResponseDTO updateItem(Long orderId, Long itemId, ItemRequestDTO itemDTO) {
        Order order = getEntityById(orderId);

        validateOrderStatus(order);

        Item itemToUpdate = itemService.updateItem(order, itemId, itemDTO);

        recalculate(order);

        orderRepository.save(order);

        return itemMapper.toDTO(itemToUpdate);
    }

    @Override
    public List<ItemResponseDTO> getItems(Long orderId) {
        Order order = getEntityById(orderId);
        return itemService.getItemsByOrder(order);
    }

    private Order getEntityById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    private void validateOrderStatus(Order order) {
        if (order.getStatus() != OrderStatus.IN_PROGRESS) {
            throw new OrderModificationNotAllowedException(order.getId(), order.getStatus().getName());
        }
    }

    private void applyDiscount(Order order, double discount) {
        order.setDiscount(discount);
        recalculate(order);
    }

    private void calculateSubtotal(Order order) {
        order.setSubtotal(
                order.getItems().stream()
                        .filter(item -> !item.getDeleted())
                        .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                        .sum()
        );
    }

    private void calculateTotal(Order order) {
        order.setTotal(order.getSubtotal() * (1 - order.getDiscount()));
    }

    private void recalculate(Order order) {
        calculateSubtotal(order);
        calculateTotal(order);
    }
}
