package com.progra3.cafeteria_api.model.dto;

import jakarta.validation.constraints.*;

public record AddressUpdateDTO(

        @Size(max = 50, message = "Street must be at most 50 characters")
        @Pattern(regexp = "^\\s*\\S.*$", message = "Street cannot be blank or only spaces")
        String street,

        @Size(max = 50, message = "City must be at most 50 characters")
        @Pattern(regexp = "^\\s*\\S.*$", message = "City cannot be blank or only spaces")
        String city,

        @Size(max = 50, message = "Province must be at most 50 characters")
        @Pattern(regexp = "^\\s*\\S.*$", message = "Province cannot be blank or only spaces")
        String province,

        @Pattern(regexp = "^\\d{4,8}$", message = "Zip code must be between 4 and 8 digits")
        String zipCode
) {}