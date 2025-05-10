package com.progra3.cafeteria_api.model.dto;

import lombok.*;

@Builder
public record SupplierRequestDTO(
    Long id,
    String legalName,
    String tradeName,
    String cuit,
    String phoneNumber,
    String email,
    String address,
    Boolean deleted
){}
