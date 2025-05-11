package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.model.dto.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;
import com.progra3.cafeteria_api.model.dto.mapper.EmployeeMapper;
import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.model.enums.Role;
import com.progra3.cafeteria_api.repository.EmployeeRepository;
import com.progra3.cafeteria_api.service.IEmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService{

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public EmployeeResponseDTO createAdmin(EmployeeRequestDTO dto){

        if (employeeRepository.existsByRole(Role.ADMIN)) {
            throw new RuntimeException("An admin already exists.");
        }

        Employee admin = employeeMapper.toEntity(dto);

        admin.setRole(Role.ADMIN);

        admin.setActive(true);

        return employeeMapper.toDTO(employeeRepository.save(admin));
    }

    @Override
    public Employee getEntityById (Long employeeId) {
        if (employeeId == null) return null;
        return employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("employeeId"));
    }

    @Override
    @Transactional
    public EmployeeResponseDTO login(String email, String password){

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if(!employee.getPassword().equals(password)){
            throw new RuntimeException("Invalid password");
        }

        if(!employee.getActive()){
            throw new RuntimeException("Employee is not active");
        }

        LoggedUser.set(employee);
        return employeeMapper.toDTO(employee);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto){
        Employee loggedUser = LoggedUser.get();
        if(loggedUser == null || loggedUser.getRole() != Role.ADMIN){
            throw new RuntimeException("Only ADMIN can create employees.");
        }

        Employee employee = employeeMapper.toEntity(dto);

        employee.setActive(true);

        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeResponseDTO deleteEmployee(Long id){
        Employee loggedUser = LoggedUser.get();
        if(loggedUser == null || loggedUser.getRole() != Role.ADMIN){
            throw new RuntimeException("Only ADMIN can delete employees.");
        }

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found."));

        if(employee.getRole() == Role.ADMIN){
            throw new RuntimeException("Cannot delete ADMIN.");
        }

        employee.setActive(false);

        return employeeMapper.toDTO(employeeRepository.save(employee));
    }
}