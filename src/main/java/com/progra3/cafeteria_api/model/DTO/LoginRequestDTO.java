package com.progra3.cafeteria_api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record LoginRequestDTO(

        @Schema(description = "User email for authentication", example = "user@example.com")
        @Email(message = "Invalid email format. Expected format: example@domain.com")
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Username is required")
        String username,

        @Schema(description = "User password for authentication", example = "MySecret123")
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password

) {}
