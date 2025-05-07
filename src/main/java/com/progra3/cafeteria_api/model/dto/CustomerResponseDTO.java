package com.progra3.cafeteria_api.model.dto;

import lombok.*;

@Builder
public record CustomerResponseDTO (
    String name,
    String lastName,
    String dni,
    String phoneNumber,
    String email,
    Double discount
){}
