package com.progra3.cafeteria_api.model.DTO.mapper;

import com.progra3.cafeteria_api.model.DTO.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.DTO.EmployeeResponseDTO;
import com.progra3.cafeteria_api.model.entity.Employee;

public class EmployeeMapper {

    public static EmployeeResponseDTO toResponseDTO(Employee employee){
        return new EmployeeResponseDTO(
                employee.getId(),
                employee.getName(),
                employee.getLastName(),
                employee.getDni(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getRole().name(),
                employee.getActive()
        );
    }

    public static Employee toEntity(EmployeeRequestDTO dto){
        Employee employee = new Employee();
        employee.setName(dto.name());
        employee.setLastName(dto.lastName());
        employee.setDni(dto.dni());
        employee.setEmail(dto.email());
        employee.setPhoneNumber(dto.phoneNumber());
        employee.setPassword(dto.password());
        employee.setRole(dto.role());
        employee.setActive(dto.active());
        return employee;
   }
}
