package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.OrderRequestDTO;
import com.progra3.cafeteria_api.model.dto.OrderResponseDTO;
import com.progra3.cafeteria_api.model.entity.Customer;
import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.model.entity.Order;
import com.progra3.cafeteria_api.model.entity.Seating;
import com.progra3.cafeteria_api.model.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final Clock clock;
    private final ItemMapper itemMapper;

    public OrderResponseDTO toDTO(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .employeeName(Optional.ofNullable(order.getEmployee())
                        .map(Employee::getName)
                        .orElse(null))
                .customerName(Optional.ofNullable(order.getCustomer())
                        .map(Customer::getName)
                        .orElse(null))
                .seatingNumber(Optional.ofNullable(order.getSeating())
                        .map(Seating::getNumber)
                        .orElse(null))
                .orderType(order.getType())
                .items(Optional.ofNullable(order.getItems())
                        .orElse(List.of())
                        .stream()
                        .map(itemMapper::toDTO)
                        .toList())
                .dateTime(order.getDateTime())
                .peopleCount(order.getPeopleCount())
                .discount(order.getDiscount())
                .status(order.getStatus().getName())
                .subtotal(order.getSubtotal())
                .total(order.getTotal())
                .build();
    }

    public Order toEntity(OrderRequestDTO dto, Employee employee, Customer customer, Seating seating) {
        return Order.builder()
                .employee(employee)
                .customer(customer)
                .seating(seating)
                .type(dto.orderType())
                .peopleCount(dto.peopleCount())
                .discount(Optional.ofNullable(customer)
                        .map(Customer::getDiscount)
                        .orElse(Order.NO_DISCOUNT))
                .status(OrderStatus.ACTIVE)
                .dateTime(LocalDateTime.now(clock))
                .build();
    }
}
