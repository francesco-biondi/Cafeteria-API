package com.progra3.cafeteria_api.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record AuditResponseDTO (
    Long id,
    LocalDateTime startTime,
    LocalDateTime closeTime,
    Double initialCash,
    List<OrderResponseDTO> orders,
    List<ExpenseResponseDTO> expenses,
    String auditStatus,
    Double total,
    Double balanceGap
){}
