package com.progra3.cafeteria_api.model.DTO;

import com.progra3.cafeteria_api.model.enums.*;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record EmployeeRequestDTO (

    @NotBlank(message = "Name is required")
    String name,

    @NotBlank(message = "Lastname is required")
    String lastName,

    @NotBlank(message = "DNI is required")
    String dni,

    @Email(message = "Invalid email format. Expected format: example@domain.com")
    @NotBlank(message = "Email is required")
    String email,

    @NotBlank(message = "Phone number is required")
    String phoneNumber,

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password,

    @NotNull(message = "Role is required")
    Role role,

    Boolean active

){}
