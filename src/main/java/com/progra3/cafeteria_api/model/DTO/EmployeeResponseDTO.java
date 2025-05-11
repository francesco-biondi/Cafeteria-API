package com.progra3.cafeteria_api.model.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

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
