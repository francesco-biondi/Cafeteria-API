package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderSplitRequestDTO(
        @Valid
        @NotNull
        OrderRequestDTO destinationOrder,

        @Valid
        @NotNull
        List<ItemTransferRequestDTO> itemsToMove
) {
}
