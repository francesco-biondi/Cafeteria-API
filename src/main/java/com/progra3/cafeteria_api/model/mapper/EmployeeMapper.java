package com.progra3.cafeteria_api.model.mapper;

import com.progra3.cafeteria_api.model.dto.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeUpdateDTO;
import com.progra3.cafeteria_api.model.entity.Employee;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {
    @Mapping(target = "role", expression = "java(employee.getRole().name())")
    EmployeeResponseDTO toDTO(Employee employee);
    Employee toEntity(EmployeeRequestDTO employeeRequestDTO);
    void updateEmployeeFromDTO(EmployeeUpdateDTO employeeUpdateDTO, @MappingTarget Employee employee);
}
