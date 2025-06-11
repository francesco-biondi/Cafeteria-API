package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

public record CustomerUpdateDTO(
        @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
        @Pattern(regexp = "^\\s*\\S.*$", message = "Name cannot be blank or only spaces")
        String name,

        @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
        @Pattern(regexp = "^\\s*\\S.*$", message = "Last name cannot be blank or only spaces")
        String lastName,

        @Pattern(regexp = "^\\d{7,8}$", message = "DNI must be between 7 and 8 numeric digits")
        String dni,

        @Pattern(regexp = "^\\d{10,13}$", message = "Phone number must be between 10 and 13 numeric digits.")
        String phoneNumber,

        @Email(message = "Invalid email format. Expected format: example@domain.com")
        String email,

        @Min(value = 0, message = "Discount must be between 0 and 100")
        @Max(value = 100, message = "Discount must be between 0 and 100")
        Integer discount) {
}