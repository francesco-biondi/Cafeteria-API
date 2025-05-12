package com.progra3.cafeteria_api.model.dto;

import com.progra3.cafeteria_api.model.enums.OrderType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponseDTO (
    Long id,
    String employeeName,
    String customerName,
    Integer seatingNumber,
    OrderType orderType,
    List<ItemResponseDTO> items,
    LocalDateTime dateTime,
    Integer peopleCount,
    Integer discount,
    String status,
    Double subtotal,
    Double total
){}
