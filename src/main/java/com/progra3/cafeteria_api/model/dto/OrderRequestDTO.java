package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderRequestDTO(

        Long employeeId,

        Long customerId,

        @NotNull(message = "Table slot ID cannot be null")
        Long tableSlotId,

        @NotNull(message = "personCount cannot be null")
        @Min(value = 1, message = "It must be at least 1 person")
        Integer peopleCount,

        @DecimalMin(value = "0.0", inclusive = true, message = "Discount percentage cannot be negative")
        @DecimalMax(value = "1.0", inclusive = true, message = "Discount percentage cannot be greater than 1")
        Double discount,

        @Valid
        List<@NotNull ItemResponseDTO> items

) {}