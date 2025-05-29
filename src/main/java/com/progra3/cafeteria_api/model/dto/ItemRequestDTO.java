package com.progra3.cafeteria_api.model.dto;

import com.progra3.cafeteria_api.validation.ValidItemRequest;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.List;

@Builder
@ValidItemRequest
public record ItemRequestDTO(
        @NotNull(message = "productId cannot be null")
        Long productId,

        @NotNull
        @Min(value = 1, message = "Minimum quantity is 1")
        Integer quantity,

        List<SelectedProductOptionRequestDTO> selectedOptions,

        String comment
) {}
