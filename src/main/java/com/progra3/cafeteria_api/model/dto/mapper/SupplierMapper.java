package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.SupplierRequestDTO;
import com.progra3.cafeteria_api.model.dto.SupplierResponseDTO;
import com.progra3.cafeteria_api.model.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {
    public SupplierResponseDTO toDTO (Supplier supplier){
        return SupplierResponseDTO.builder()
                .legalName(supplier.getLegalName())
                .tradeName(supplier.getTradeName())
                .cuit(supplier.getCuit())
                .phoneNumber(supplier.getPhoneNumber())
                .email(supplier.getEmail())
                .address(supplier.getAddress())
                .deleted(supplier.getDeleted())
                .build();
    }

    public Supplier toEntity (SupplierRequestDTO dto){
        return Supplier.builder()
                .id(id)
                .legalName(dto.legalName())
                .tradeName(dto.tradeName())
                .cuit(dto.cuit())
                .email(dto.email())
                .address(dto.address())
                .phoneNumber(dto.phoneNumber())
                .deleted(dto.deleted())
                .build();
    }
}