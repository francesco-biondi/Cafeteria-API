package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;
import com.progra3.cafeteria_api.model.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    public EmployeeResponseDTO toDTO(Employee employee){
        return EmployeeResponseDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .lastName(employee.getLastName())
                .dni(employee.getDni())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .role(employee.getRole().name())
                .active(employee.getActive())
                .build();
    }

    public Employee toEntity(EmployeeRequestDTO dto){
        return Employee.builder()
                .name(dto.name())
                .lastName(dto.lastName())
                .dni(dto.dni())
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .password(dto.password())
                .role(dto.role())
                .build();
   }
}
