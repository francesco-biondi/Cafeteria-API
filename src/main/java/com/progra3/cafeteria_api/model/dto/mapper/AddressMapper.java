package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.AddressRequestDTO;
import com.progra3.cafeteria_api.model.dto.AddressResponseDTO;
import com.progra3.cafeteria_api.model.dto.AddressUpdateDTO;
import com.progra3.cafeteria_api.model.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressResponseDTO toDTO(Address address) {
        return AddressResponseDTO.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .province(address.getProvince())
                .zipCode(address.getZipCode())
                .build();
    }

    public Address toEntity(AddressRequestDTO dto){
        return Address.builder()
                .street(dto.street())
                .city(dto.city())
                .province(dto.province())
                .zipCode(dto.zipCode())
                .build();
    }

    public Address toEntity(AddressUpdateDTO dto, Address entity){
        return Address.builder()
                .street(isNullOrBlank(dto.street()) ? entity.getStreet() : dto.street())
                .city(isNullOrBlank(dto.city()) ? entity.getCity() : dto.city())
                .province(isNullOrBlank(dto.province()) ? entity.getProvince() : dto.province())
                .zipCode(isNullOrBlank(dto.zipCode()) ? entity.getZipCode() : dto.zipCode())
                .build();
    }

    private boolean isNullOrBlank(String s){
        return s == null || s.trim().isEmpty();
    }
}
