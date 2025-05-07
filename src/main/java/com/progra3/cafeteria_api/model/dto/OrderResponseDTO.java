package com.progra3.cafeteria_api.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
@Data
public class OrderResponseDTO {
    private Long id;
    private String employeeName;
    private String customerName;
    private Integer table;
    private List<ItemResponseDTO> items;
    private LocalDate date;
    private LocalTime time;
    private Integer peopleCount;
    private Double discount;
    private String status;
    private Double subtotal;
    private Double total;
}
