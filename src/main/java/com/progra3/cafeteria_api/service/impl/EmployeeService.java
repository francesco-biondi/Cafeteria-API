package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.model.dto.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;
import com.progra3.cafeteria_api.model.dto.mapper.EmployeeMapper;
import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.model.enums.Role;
import com.progra3.cafeteria_api.repository.EmployeeRepository;
import com.progra3.cafeteria_api.service.IEmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements IEmployeeService{

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public EmployeeResponseDTO createAdmin(EmployeeRequestDTO dto){

        if (employeeRepository.existsByRole(Role.ADMIN)) {
            throw new RuntimeException("An admin already exists.");
        }

        Employee admin = new Employee();
        admin.setName(dto.name());
        admin.setLastName(dto.lastName());
        admin.setDni(dto.dni());
        admin.setEmail(dto.email());
        admin.setPhoneNumber(dto.phoneNumber());
        admin.setPassword(dto.password());
        admin.setRole(Role.ADMIN);
        admin.setActive(true);

        admin = employeeRepository.save(admin);
        return EmployeeMapper.toResponseDTO(admin);
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
        return EmployeeMapper.toResponseDTO(employee);

    }

    @Override
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO){
        Employee loggedUser = LoggedUser.get();
        if(loggedUser == null || loggedUser.getRole() != Role.ADMIN){
            throw new RuntimeException("Only ADMIN can create employees.");
        }

        Employee employee = new Employee();
        employee.setName(requestDTO.name());
        employee.setLastName(requestDTO.lastName());
        employee.setDni(requestDTO.dni());
        employee.setEmail(requestDTO.email());
        employee.setPhoneNumber(requestDTO.phoneNumber());
        employee.setPassword(requestDTO.password());
        employee.setRole(requestDTO.role());
        employee.setActive(true);

        Employee saved = employeeRepository.save(employee);
        return EmployeeMapper.toResponseDTO(saved);
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
        Employee updated = employeeRepository.save(employee);
        return EmployeeMapper.toResponseDTO(updated);
    }

}