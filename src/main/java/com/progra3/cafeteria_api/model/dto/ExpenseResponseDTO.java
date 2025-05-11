package com.progra3.cafeteria_api.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
public record ExpenseResponseDTO (
    Long id,
    AuditResponseDTO audit,
    SupplierResponseDTO supplier,
    Double amount,
    String comment,
    LocalDateTime date
){}