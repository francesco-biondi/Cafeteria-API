package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.Positive;

public record ExpenseUpdateDTO(
        Long supplierId,

        @Positive(message = "Amount must be positive")
        Double amount,

        String comment
) {}
