package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.NotNull;

public record ItemTransferRequestDTO(
        @NotNull
        Long itemId,

        @NotNull
        Integer quantity
) {
}
