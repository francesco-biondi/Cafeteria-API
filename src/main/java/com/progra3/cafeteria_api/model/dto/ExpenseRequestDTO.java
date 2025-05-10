package com.progra3.cafeteria_api.model.dto;

import lombok.*;
import java.time.LocalDateTime;

@Builder
public record ExpenseRequestDTO (
     Long id,
     Long supplierId,
     Double amount,
     String comment,
     LocalDateTime date
){}