package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;
import com.progra3.cafeteria_api.model.entity.Employee;

public interface IEmployeeService {

    EmployeeResponseDTO createAdmin(EmployeeRequestDTO requestDTO);
    Employee getEntityById (Long employeeId);
    EmployeeResponseDTO login(String email, String password);
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO);
    EmployeeResponseDTO deleteEmployee(Long id);
}
