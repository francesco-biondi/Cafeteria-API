package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.SupplierRequestDTO;
import com.progra3.cafeteria_api.model.dto.SupplierResponseDTO;
import com.progra3.cafeteria_api.model.dto.SupplierUpdateDTO;
import com.progra3.cafeteria_api.model.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {
    public SupplierResponseDTO toDTO (Supplier supplier){
        return SupplierResponseDTO.builder()
                .id(supplier.getId())
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
                .legalName(dto.legalName())
                .tradeName(dto.tradeName())
                .cuit(dto.cuit())
                .phoneNumber(dto.phoneNumber())
                .email(dto.email())
                .address(dto.address())
                .build();
    }

    public Supplier toEntity (SupplierUpdateDTO dto, Supplier existingSupplier){
        return Supplier.builder()
                .id(existingSupplier.getId())
                .legalName(isNullOrBlank(dto.legalName()) ? existingSupplier.getLegalName() : dto.legalName())
                .tradeName(isNullOrBlank(dto.tradeName()) ? existingSupplier.getTradeName() : dto.tradeName())
                .cuit(isNullOrBlank(dto.cuit()) ? existingSupplier.getCuit() : dto.cuit())
                .phoneNumber(isNullOrBlank(dto.phoneNumber()) ? existingSupplier.getPhoneNumber() : dto.phoneNumber())
                .email(isNullOrBlank(dto.email()) ? existingSupplier.getEmail() : dto.email())
                .address(isNullOrBlank(dto.address()) ? existingSupplier.getAddress() : dto.address())
                .deleted(false)
                .build();
    }

    private boolean isNullOrBlank(String s){
        return s == null || s.trim().isEmpty();
    }
}