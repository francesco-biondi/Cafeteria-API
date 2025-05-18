package com.progra3.cafeteria_api.model.dto;

import com.progra3.cafeteria_api.model.enums.*;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record EmployeeRequestDTO(

        @NotBlank(message = "Name is required")
        @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
        String name,

        @NotBlank(message = "Lastname is required")
        @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
        String lastName,

        @NotBlank(message = "DNI is required")
        @Pattern(regexp = "^\\d{7,8}$", message = "DNI must be between 7 and 8 numeric digits")
        String dni,

        @Email(message = "Invalid email format. Expected format: example@domain.com")
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^\\d{10,13}$", message = "Phone number must be between 10 and 13 numeric digits")
        String phoneNumber,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,

        @NotNull(message = "Role is required")
        Role role,

        @NotNull(message = "Deleted is required")
        Boolean deleted

) {
}
