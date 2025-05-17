package com.progra3.cafeteria_api.model.dto;

import lombok.Builder;

@Builder
public record AddressResponseDTO(
        String street,
        String city,
        String province,
        String zipCode
) {}