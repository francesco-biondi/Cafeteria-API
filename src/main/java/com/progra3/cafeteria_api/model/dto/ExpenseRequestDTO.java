package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
public record ExpenseRequestDTO (

     Long id,

     @NotBlank(message = "Each expense must have a supplier")
     @Positive(message = "Id must be positive")
     Long supplierId,

     @NotBlank(message = "Ammount cannot be null")
     @Positive(message = "Ammount must be positive")
     Double amount,

     String comment
){}