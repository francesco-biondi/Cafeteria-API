package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.CustomerRequestDTO;
import com.progra3.cafeteria_api.model.dto.CustomerResponseDTO;
import com.progra3.cafeteria_api.model.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerResponseDTO toDTO (Customer customer){
        return CustomerResponseDTO.builder()
                .name(customer.getName())
                .lastName(customer.getLastName())
                .dni(customer.getDni())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .discount(customer.getDiscount())
                .build();
    }

    public Customer toEntity (CustomerRequestDTO dto){
        return Customer.builder()
                .name(dto.name())
                .lastName(dto.lastName())
                .dni(dto.dni())
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .discount(dto.discount())
                .build();
    }
}
