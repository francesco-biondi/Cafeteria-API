package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SupplierUpdateDTO(
        @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
        String legalName,

        @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
        String tradeName,

        @Pattern(
                regexp = "^\\d{2}-\\d{8}-\\d$",
                message = "CUIT must have XX-XXXXXXXX-X format"
        )
        String cuit,

        @Pattern(regexp = "^\\d{10,13}$", message = "Phone number must be between 10 and 13 numeric digits.")
        String phoneNumber,

        @Email(message = "Invalid email format. Expected format: example@domain.com")
        String email,

        @Valid
        AddressUpdateDTO address
) {}