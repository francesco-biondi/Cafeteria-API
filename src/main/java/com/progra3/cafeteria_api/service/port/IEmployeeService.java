package com.progra3.cafeteria_api.service.port;

import com.progra3.cafeteria_api.model.dto.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeUpdateDTO;
import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.model.enums.Role;

import java.util.List;

public interface IEmployeeService {
    EmployeeResponseDTO createEmployeeOrAdmin(EmployeeRequestDTO dto);
    EmployeeResponseDTO getEmployeeById(Long id);
    List<EmployeeResponseDTO> getAllEmployees();
    List<EmployeeResponseDTO> filterEmployees(String name, String lastName, String dni, String email, String phoneNumber, Role role, Boolean deleted);
    EmployeeResponseDTO updateEmployee(Long id, EmployeeUpdateDTO updateDTO);
    EmployeeResponseDTO deleteEmployee(Long id);

    Employee getEntityById (Long employeeId);
}