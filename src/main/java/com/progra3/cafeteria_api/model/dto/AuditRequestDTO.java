package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.time.LocalDateTime;

@Builder
public record AuditRequestDTO (
    Double initialCash
){}