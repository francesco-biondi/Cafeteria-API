package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeUpdateDTO;
import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.model.enums.Role;

import java.util.List;

public interface IEmployeeService {

    EmployeeResponseDTO createAdmin(EmployeeRequestDTO requestDTO);
    Employee getEntityById (Long employeeId);
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO);
    EmployeeResponseDTO deleteEmployee(Long id);
    EmployeeResponseDTO updateEmployee(Long id, EmployeeUpdateDTO updateDTO);
    List<EmployeeResponseDTO> getAllEmployees();
    List<EmployeeResponseDTO> filterEmployees(String name, String lastName, String dni, String email, String phoneNumber, Role role, Boolean deleted);
}
