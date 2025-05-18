package com.progra3.cafeteria_api.model.dto;

import lombok.Builder;
import com.progra3.cafeteria_api.model.enums.*;

@Builder
public record EmployeeUpdateDTO(

        String name,
        String lastName,
        String dni,
        String phoneNumber,
        String email,
        String password,
        Role role

){

}



