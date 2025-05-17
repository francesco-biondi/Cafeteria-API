package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.*;

public record AddressUpdateDTO(
        @Size(min = 1, max = 50, message = "Street must be between 1 and 50 characters")
        String street,

        @Size(min = 1, max = 50, message = "City must be between 1 and 50 characters")
        String city,

        @Size(min = 1, max = 50, message = "Province must be between 1 and 50 characters")
        String province,

        @Pattern(regexp = "^\\d{4,8}$", message = "Zip code must be between 4 and 8 digits")
        String zipCode
) {}