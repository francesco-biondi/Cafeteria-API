package com.progra3.cafeteria_api.model.dto.mapper;


import com.progra3.cafeteria_api.model.dto.CustomerRequestDTO;
import com.progra3.cafeteria_api.model.dto.CustomerResponseDTO;
import com.progra3.cafeteria_api.model.dto.CustomerUpdateDTO;
import com.progra3.cafeteria_api.model.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerResponseDTO toDTO (Customer customer){
        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .lastName(customer.getLastName())
                .dni(customer.getDni())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .discount(customer.getDiscount())
                .deleted(customer.getDeleted())
                .build();
    }

    public Customer toEntity (CustomerRequestDTO dto){
        return Customer.builder()
                .name(dto.name())
                .lastName(dto.lastName())
                .dni(dto.dni())
                .phoneNumber(dto.phoneNumber())
                .email(dto.email())
                .discount(dto.discount())
                .build();
    }

    public Customer toEntity(CustomerUpdateDTO dto, Customer existingCustomer) {
        return Customer.builder()
                .id(existingCustomer.getId())
                .name(isNullOrBlank(dto.name()) ? existingCustomer.getName() : dto.name())
                .lastName(isNullOrBlank(dto.lastName()) ? existingCustomer.getLastName() : dto.lastName())
                .dni(isNullOrBlank(dto.dni()) ? existingCustomer.getDni() : dto.dni())
                .phoneNumber(isNullOrBlank(dto.phoneNumber()) ? existingCustomer.getPhoneNumber() : dto.phoneNumber())
                .email(isNullOrBlank(dto.email()) ? existingCustomer.getEmail() : dto.email())
                .discount(dto.discount() != null ? dto.discount() : existingCustomer.getDiscount())
                .deleted(false)
                .build();
    }

    private boolean isNullOrBlank(String s){
        return s == null || s.trim().isEmpty();
    }
}