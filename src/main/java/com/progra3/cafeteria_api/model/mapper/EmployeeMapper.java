package com.progra3.cafeteria_api.model.mapper;

import com.progra3.cafeteria_api.model.dto.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeUpdateDTO;
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
                .deleted(employee.getDeleted())
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
                .deleted(false)
                .build();
   }

   public Employee toEntity(EmployeeUpdateDTO dto, Employee existingEmployee){
        return Employee.builder()
                .id(existingEmployee.getId())
                .name(isNullOrBlank(dto.name()) ? existingEmployee.getName() : dto.name())
                .lastName(isNullOrBlank(dto.lastName()) ? existingEmployee.getLastName() : dto.lastName())
                .dni(isNullOrBlank(dto.dni()) ? existingEmployee.getDni() : dto.dni())
                .email(isNullOrBlank(dto.email()) ? existingEmployee.getEmail() : dto.email())
                .phoneNumber(isNullOrBlank(dto.phoneNumber()) ? existingEmployee.getPhoneNumber() : dto.phoneNumber())
                .password(isNullOrBlank(dto.password()) ? existingEmployee.getPassword() : dto.password())
                .role(dto.role() == null ? existingEmployee.getRole() : dto.role())
                .deleted(false)
                .build();
   }

    private boolean isNullOrBlank(String s){
        return s == null || s.trim().isEmpty();
    }
}