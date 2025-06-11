package com.progra3.cafeteria_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private final String token;
    private final EmployeeResponseDTO employee;
}