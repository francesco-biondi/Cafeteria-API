package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Builder
public record ExpenseRequestDTO (
     @NotNull(message = "Each expense must have a supplier")
     Long supplierId,

     @NotNull(message = "Ammount cannot be null")
     @Positive(message = "Ammount must be positive")
     Double amount,

     String comment
){}