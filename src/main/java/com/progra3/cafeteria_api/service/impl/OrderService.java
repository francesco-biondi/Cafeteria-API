package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.InvalidDateException;
import com.progra3.cafeteria_api.exception.OrderModificationNotAllowedException;
import com.progra3.cafeteria_api.exception.OrderNotFoundException;
import com.progra3.cafeteria_api.model.dto.*;
import com.progra3.cafeteria_api.model.dto.mapper.ItemMapper;
import com.progra3.cafeteria_api.model.dto.mapper.OrderMapper;
import com.progra3.cafeteria_api.model.entity.*;
import com.progra3.cafeteria_api.model.enums.OrderStatus;
import com.progra3.cafeteria_api.model.enums.SeatingStatus;
import com.progra3.cafeteria_api.repository.OrderRepository;
import com.progra3.cafeteria_api.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final SeatingService seatingService;
    private final ItemService itemService;

    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;

    @Override
    public OrderResponseDTO create(OrderRequestDTO dto) {
        Order order = createNewOrder(dto);

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
    public List<OrderResponseDTO> getBySeating(Long seatingId) {
        return orderRepository.findBySeatingId(seatingId)
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
    public List<Order> getByDateTimeBetween(LocalDateTime start, LocalDateTime end){
        if (start.isAfter(end)){
            throw new InvalidDateException("Start should be earlier than end");
        }
        return orderRepository.findByDateTimeBetween(start, end);
    }

    @Override
    public OrderResponseDTO update(OrderRequestDTO dto) throws OrderNotFoundException, IllegalStateException {
        Order order = orderMapper.toEntity(dto,
                employeeService.getEntityById(dto.employeeId()),
                customerService.getEntityById(dto.customerId()),
                seatingService.getEntityById(dto.seatingId()));

        recalculate(order);

        return orderMapper.toDTO(orderRepository.save(order));
    }

    @Override
    public OrderResponseDTO updateDiscount(Long orderId, Integer discount) {
        Order order = getEntityById(orderId);

        validateOrderStatus(order.getStatus());

        applyDiscount(order, discount);

        return orderMapper.toDTO(orderRepository.save(order));
    }

    @Override
    public OrderResponseDTO updateStatus(Long id, OrderStatus status){
        Order order = getEntityById(id);

        if (status.equals(OrderStatus.BILLED))
            seatingService.updateStatus(order.getSeating().getId(), SeatingStatus.BILLING);
        else
            seatingService.updateStatus(order.getSeating().getId(), SeatingStatus.FREE);

        validateOrderStatus(order.getStatus());

        order.setStatus(status);

        return orderMapper.toDTO(orderRepository.save(order));
    }

    @Override
    public List<OrderResponseDTO> splitOrder(Long originalOrderId, OrderRequestDTO dto, Map<Long, Integer> itemsToMove) {

        Order originalOrder = getEntityById(originalOrderId);
        validateOrderStatus(originalOrder.getStatus());

        if (itemsToMove == null || itemsToMove.isEmpty())
            throw new IllegalArgumentException("Items to move cannot be null or empty.");
        if (originalOrder.getPeopleCount() < dto.peopleCount())
            throw new IllegalArgumentException("Invalid number of people to move.");

        Order destinationOrder = orderRepository.findBySeatingId(dto.seatingId())
                .orElseGet(() -> createNewOrder(dto));

        //TODO Fran revisa trasferItems pide Map<Long, Integer> itemsToMove, pero desde controller viene Map<Long, ItemRequestDTO>
        //TODO Lo que te quiero decir que es diferente el formato, por eso tira error
        List<Item> itemsToTransfer = itemService.transferItems(originalOrder, destinationOrder, itemsToMove);
        destinationOrder.setItems(itemsToTransfer);

        recalculate(originalOrder);
        recalculate(destinationOrder);

        originalOrder = orderRepository.save(originalOrder);
        destinationOrder = orderRepository.save(destinationOrder);

        return List.of(orderMapper.toDTO(originalOrder), orderMapper.toDTO(destinationOrder));
    }


    @Override
    public ItemResponseDTO addItem(Long orderId, ItemRequestDTO itemDTO) {
        Order order = getEntityById(orderId);

        validateOrderStatus(order.getStatus());

        Item newItem = itemService.createItem(order, itemDTO);

        order.getItems().add(newItem);
        recalculate(order);

        orderRepository.save(order);

        return itemMapper.toDTO(newItem);
    }

    @Override
    public ItemResponseDTO removeItem(Long orderId, Long itemId) {
        Order order = getEntityById(orderId);

        validateOrderStatus(order.getStatus());

        Item itemToRemove = itemService.getItemById(order, itemId);
        itemToRemove.setDeleted(true);

        recalculate(order);
        orderRepository.save(order);

        return itemMapper.toDTO(itemToRemove);
    }

    @Override
    public ItemResponseDTO updateItem(Long orderId, ItemRequestDTO itemDTO) {
        Order order = getEntityById(orderId);

        validateOrderStatus(order.getStatus());

        Item itemToUpdate = itemService.updateItem(order, itemDTO);

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

    private void validateOrderStatus(OrderStatus orderStatus) {
        if (orderStatus != OrderStatus.ACTIVE) {
            throw new OrderModificationNotAllowedException(orderStatus.getName());
        }
    }

    private Order createNewOrder(OrderRequestDTO dto) {
        Employee employee = employeeService.getEntityById(dto.employeeId());
        Customer customer = customerService.getEntityById(dto.customerId());
        Seating seating = null;
        if (dto.seatingId() != null)
            seating = seatingService.getEntityById(dto.seatingId());

        return orderMapper.toEntity(dto, employee, customer, seating);
    }

    private void applyDiscount(Order order, Integer discount) {
        order.setDiscount(discount);
        recalculate(order);
    }

    private void calculateSubtotal(Order order) {
        order.setSubtotal(
                order.getItems().stream()
                        .filter(item -> !item.getDeleted())
                        .mapToDouble(Item::getTotalPrice)
                        .sum()
        );
    }

    private void calculateTotal(Order order) {
        order.setTotal(order.getSubtotal() * (1 - order.getDiscount() / 100));
    }

    private void recalculate(Order order) {
        calculateSubtotal(order);
        calculateTotal(order);
    }
}
