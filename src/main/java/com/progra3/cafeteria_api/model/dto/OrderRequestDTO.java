package com.progra3.cafeteria_api.model.dto;

import com.progra3.cafeteria_api.model.dto.validation.ValidOrderRequest;
import com.progra3.cafeteria_api.model.enums.OrderType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.List;

@Builder
@ValidOrderRequest
public record OrderRequestDTO(

        Long employeeId,

        Long customerId,

        @NotNull(message = "Seating ID cannot be null")
        Long seatingId,

        @NotNull(message = "Order type cannot be null")
        OrderType orderType,

        @NotNull(message = "People count cannot be null")
        @Min(value = 1, message = "It must be at least 1 person")
        Integer peopleCount,

        @Min(value = 0, message = "Discount must be greater than or equal to 0")
        @Max(value = 100, message = "Discount must be less than or equal to 100")
        Integer discount,

        @Valid
        List<@NotNull ItemResponseDTO> items

) {}