package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.Valid;

import java.util.List;

public record OrderSplitRequestDTO(
        @Valid
        OrderRequestDTO order,

        @Valid
        List<ItemRequestDTO> itemsToMove
) {}
