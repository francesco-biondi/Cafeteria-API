package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.OrderRequestDTO;
import com.progra3.cafeteria_api.model.dto.OrderResponseDTO;
import com.progra3.cafeteria_api.model.entity.Order;
import com.progra3.cafeteria_api.model.entity.TableSlot;
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

    public OrderResponseDTO toDTO(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .employeeName(order.getEmployee() != null ? order.getEmployee().getName() : null)
                .customerName(order.getCustomer() != null ? order.getCustomer().getName() : null)
                .table(order.getTable().getNumber())
                .items(order.getItems()
                        .stream()
                        .map(ItemMapper::toDTO)
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

    public Order toEntity(OrderRequestDTO dto, Employee employee, Customer customer, TableSlot tableSlot) {
        return Order.builder()
                .employee(employee)
                .customer(customer)
                .tableSlot(tableSlot)
                .discount(customer.getDiscount().orElse(Order.NO_DISCOUNT))
                .peopleCount(dto.getPeopleCount())
                .status(OrderStatus.IN_PROGRESS)
                .date(LocalDate.now(clock))
                .time(LocalTime.now(clock))
                .build();
    }
}
