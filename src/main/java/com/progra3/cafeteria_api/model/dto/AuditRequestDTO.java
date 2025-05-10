package com.progra3.cafeteria_api.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
public record AuditRequestDTO (
    Long id,
    LocalDateTime startTime,
    Double initialCash
){}