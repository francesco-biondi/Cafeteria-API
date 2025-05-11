package com.progra3.cafeteria_api.model.dto;

import com.progra3.cafeteria_api.model.dto.validation.ValidOrderRequest;
import com.progra3.cafeteria_api.model.enums.OrderType;
import jakarta.validation.constraints.*;
import lombok.Builder;


@Builder
@ValidOrderRequest
public record OrderRequestDTO(
        Long id,

        Long seatingId,

        Long employeeId,

        Long customerId,

        @NotNull(message = "Order type cannot be null")
        OrderType orderType,

        @Min(value = 1, message = "It must be at least 1 person")
        Integer peopleCount,

        @Size(max = 100, message = "Discount must be between 0 and 100")
        Integer discount
) {}