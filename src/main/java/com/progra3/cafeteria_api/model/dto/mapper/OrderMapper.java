package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.OrderRequestDTO;
import com.progra3.cafeteria_api.model.dto.OrderResponseDTO;
import com.progra3.cafeteria_api.model.entity.Order;
import com.progra3.cafeteria_api.model.entity.Seating;
import com.progra3.cafeteria_api.model.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final Clock clock;
    private final ItemMapper itemMapper;

    public OrderResponseDTO toDTO(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .employeeName(order.getEmployee() != null ? order.getEmployee().getName() : null)
                .customerName(order.getCustomer() != null ? order.getCustomer().getName() : null)
                .table(order.getSeating().getNumber())
                .items(order.getItems()
                        .stream()
                        .map(itemMapper::toDTO)
                        .toList())
                .date(order.getDate())
                .time(order.getTime())
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
                .discount(customer.getDiscount().orElse(Order.NO_DISCOUNT))
                .status(OrderStatus.ACTIVE)
                .date(LocalDate.now(clock))
                .time(LocalTime.now(clock))
                .build();
    }
}
