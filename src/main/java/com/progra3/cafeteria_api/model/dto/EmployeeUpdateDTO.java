package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import com.progra3.cafeteria_api.model.enums.*;

@Builder
public record EmployeeUpdateDTO(

        @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
        String name,

        @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
        String lastName,

        @Pattern(regexp = "^\\d{7,8}$", message = "DNI must be between 7 and 8 numeric digits")
        String dni,

        @Email(message = "Invalid email format. Expected format: example@domain.com")
        String email,

        @Pattern(regexp = "^\\d{10,13}$", message = "Phone number must be between 10 and 13 numeric digits")
        String phoneNumber,

        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,

        Role role
){}



