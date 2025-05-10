package com.progra3.cafeteria_api.model.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Builder
public record CustomerRequestDTO (
    Long id,

    @NotBlank(message = "Name cannot be null")
    String name,

    @NotBlank(message = "Last name cannot be null")
    String lastName,

    @NotBlank(message = "DNI cannot be null")
    @Pattern(regexp = "^\\d{7,8}$", message = "DNI must be between 7 and 8 numeric digits")
    String dni,

    @NotBlank(message = "Phone number cannot be null")
    @Pattern(regexp = "^\\d{10,13}$", message = "Phone number must be between 10 and 13 numeric digits.")
    String phoneNumber,

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Invalid mail")
    String email,
    Double discount
){}
