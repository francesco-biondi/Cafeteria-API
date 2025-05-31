package com.progra3.cafeteria_api.model.dto;

import com.progra3.cafeteria_api.validation.ValidOrderRequest;
import com.progra3.cafeteria_api.model.enums.OrderType;
import jakarta.validation.constraints.*;
import lombok.Builder;


@Builder
@ValidOrderRequest
public record OrderRequestDTO(
        Long seatingId,

        Long employeeId,

        Long customerId,

        @NotNull(message = "Order type cannot be null")
        OrderType orderType,

        @Min(value = 1, message = "It must be at least 1 person")
        Integer peopleCount
) {}