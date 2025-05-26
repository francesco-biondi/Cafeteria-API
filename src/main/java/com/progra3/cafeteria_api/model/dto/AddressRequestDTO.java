package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record AddressRequestDTO(
        @NotBlank(message = "Street cannot be null")
        @Size(min = 1, max = 50, message = "Street must be between 1 and 50 characters")
        String street,

        @NotBlank(message = "City cannot be null")
        @Size(min = 1, max = 50, message = "City must be between 1 and 50 characters")
        String city,

        @NotBlank(message = "Province cannot be null")
        @Size(min = 1, max = 50, message = "Province must be between 1 and 50 characters")
        String province,

        @NotBlank(message = "Zip code cannot be null")
        @Pattern(regexp = "^\\d{4,8}$", message = "Zip code must be between 4 and 8 digits")
        String zipCode
) {}
