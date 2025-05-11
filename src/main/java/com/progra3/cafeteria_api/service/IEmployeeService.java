package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;

public interface IEmployeeService {

    EmployeeResponseDTO createAdmin(EmployeeRequestDTO requestDTO);
    EmployeeResponseDTO login(String email, String password);
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO);
    EmployeeResponseDTO deleteEmployee(Long id);
}
