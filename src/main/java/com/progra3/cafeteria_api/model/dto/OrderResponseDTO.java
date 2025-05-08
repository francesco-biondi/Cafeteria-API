package com.progra3.cafeteria_api.model.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
public record OrderResponseDTO (
    Long id,
    String employeeName,
    String customerName,
    Integer table,
    List<ItemResponseDTO> items,
    LocalDate date,
    LocalTime time,
    Integer peopleCount,
    Integer discount,
    String status,
    Double subtotal,
    Double total
){}
