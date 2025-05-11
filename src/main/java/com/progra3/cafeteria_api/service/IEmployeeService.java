package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.DTO.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.DTO.EmployeeResponseDTO;

public interface IEmployeeService {

    EmployeeResponseDTO createAdmin(EmployeeRequestDTO requestDTO);
    EmployeeResponseDTO login(String email, String password);
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO);
    EmployeeResponseDTO deleteEmployee(Long id);
}
