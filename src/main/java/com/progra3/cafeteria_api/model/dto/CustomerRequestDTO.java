package com.progra3.cafeteria_api.model.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Builder
public record CustomerRequestDTO (
    @NotBlank(message = "Name cannot be null")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    String name,

    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    String lastName,

    @Pattern(regexp = "^\\d{7,8}$", message = "DNI must be between 7 and 8 numeric digits")
    String dni,

    @Pattern(regexp = "^\\d{10,13}$", message = "Phone number must be between 10 and 13 numeric digits.")
    String phoneNumber,

    @Email(message = "Invalid email format. Expected format: example@domain.com")
    String email,

    @Size(max = 100, message = "Discount must be between 0 and 100")
    Integer discount
){}
