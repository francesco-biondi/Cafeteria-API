package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record BusinessRequestDTO(

        @NotBlank
        String name,

        @NotBlank
        String cuit,

        @Valid
        AddressRequestDTO address,

        @Valid
        EmployeeRequestDTO owner
) {
}
