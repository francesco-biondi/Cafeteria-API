package com.progra3.cafeteria_api.model.dto;

public record BusinessResponseDTO(
        Long id,
        String name,
        String cuit,
        AddressResponseDTO address
) {
}
