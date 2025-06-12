package com.progra3.cafeteria_api.model.dto;

import com.progra3.cafeteria_api.validation.ValidBusinessRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ValidBusinessRequest
public record BusinessRequestDTO(

        @NotBlank
        String name,

        @NotBlank
        String cuit,

        @Valid
        AddressRequestDTO address,

        @NotNull
        @Valid
        EmployeeRequestDTO owner
) {
}
