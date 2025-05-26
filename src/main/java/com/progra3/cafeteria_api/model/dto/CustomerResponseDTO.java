package com.progra3.cafeteria_api.model.dto;

import lombok.*;

@Builder
public record CustomerResponseDTO (
    Long id,
    String name,
    String lastName,
    String dni,
    String phoneNumber,
    String email,
    Boolean deleted,
    Integer discount
){}
