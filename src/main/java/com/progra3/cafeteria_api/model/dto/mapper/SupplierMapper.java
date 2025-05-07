package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.SupplierRequestDTO;
import com.progra3.cafeteria_api.model.dto.SupplierResponseDTO;
import com.progra3.cafeteria_api.model.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {
    public SupplierResponseDTO toDTO (Supplier supplier){
        return SupplierResponseDTO.builder()
                .name(supplier.getName())
                .lastName(supplier.getLastName())
                .dni(supplier.getDni())
                .phoneNumber(supplier.getPhoneNumber())
                .email(supplier.getEmail())
                .address(supplier.getAddress())
                .build();
    }

    public Supplier toEntity (SupplierRequestDTO dto){
        return Supplier.builder()
                .name(dto.name())
                .lastName(dto.lastName())
                .dni(dto.dni())
                .email(dto.email())
                .address(dto.address())
                .phoneNumber(dto.phoneNumber())
                .build();
    }
}