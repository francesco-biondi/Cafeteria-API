package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
public record SupplierRequestDTO(
    Long id,

    @NotBlank(message = "Legal name cannot be null")
    String legalName,

    @NotBlank(message = "Trade name cannot be null")
    String tradeName,

    @Pattern(
            regexp = "^\\d{2}-\\d{8}-\\d$",
            message = "CUIT must have XX-XXXXXXXX-X format"
    )
    String cuit,

    @NotBlank(message = "Phone number cannot be null")
    @Pattern(regexp = "^\\d{10,13}$", message = "Phone number must be between 10 and 13 numeric digits.")
    String phoneNumber,

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Invalid mail")
    String email,

    //TODO change String with an actual Adress class
    String address
){}
