package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Builder
public record ExpenseRequestDTO (
     @NotNull(message = "Each expense must have a supplier")
     Long supplierId,

     @NotNull(message = "Amount cannot be null")
     @Positive(message = "Amount must be positive")
     Double amount,

     @Size(max = 255, message = "Comment must have max 255 characters")
     String comment
){}