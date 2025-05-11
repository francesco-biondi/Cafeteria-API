package com.progra3.cafeteria_api.model.dto;

public record EmployeeResponseDTO(
        Long id,
        String name,
        String lastName,
        String dni,
        String phoneNumber,
        String email,
        String role,
        Boolean active
){}
