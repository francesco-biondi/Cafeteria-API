package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.*;
import com.progra3.cafeteria_api.model.dto.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeUpdateDTO;
import com.progra3.cafeteria_api.model.dto.mapper.EmployeeMapper;
import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.model.enums.Role;
import com.progra3.cafeteria_api.repository.EmployeeRepository;
import com.progra3.cafeteria_api.service.IEmployeeService;
import com.progra3.cafeteria_api.specification.EmployeeSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService{

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public EmployeeResponseDTO createAdmin(EmployeeRequestDTO dto){

        if (employeeRepository.existsByRole(Role.ADMIN)) {
            throw new AdminExistsException();
        }

        Employee admin = employeeMapper.toEntity(dto);

        admin.setRole(Role.ADMIN);

        admin.setDeleted(false);

        return employeeMapper.toDTO(employeeRepository.save(admin));
    }

    @Override
    public Employee getEntityById (Long employeeId) {
        if (employeeId == null) return null;
        return employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    @Override
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto){
        Employee loggedUser = LoggedUser.get();
        if(loggedUser == null || loggedUser.getRole() != Role.ADMIN){
            throw new EmployeeCreationPermissionException();
        }

        Employee employee = employeeMapper.toEntity(dto);

        employee.setDeleted(false);

        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeResponseDTO deleteEmployee(Long id){
        Employee loggedUser = LoggedUser.get();
        if(loggedUser == null || loggedUser.getRole() != Role.ADMIN){
            throw new EmployeeDeletionPermission();
        }

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        if(employee.getRole() == Role.ADMIN){
            throw new AdminDeletionException();
        }

        employee.setDeleted(true);

        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeUpdateDTO dto){
        Employee loggedUser = LoggedUser.get();
        if(loggedUser == null || loggedUser.getRole() != Role.ADMIN){
            throw new EmployeeCreationPermissionException();
        }

        Employee employee = employeeRepository.findById(id).
                orElseThrow(() ->  new EmployeeNotFoundException(id));

        if(dto.name() != null){
            employee.setName(dto.name());
        }

        if(dto.lastName() != null){
            employee.setLastName(dto.lastName());
        }

        if(dto.dni() != null){
            employee.setDni(dto.dni());
        }

        if(dto.phoneNumber() != null){
            employee.setPhoneNumber(dto.phoneNumber());
        }

        if(dto.email() != null){
            employee.setEmail(dto.email());
        }

        if(dto.password() != null){
            employee.setPassword(dto.password());
        }

        if(dto.role() != null){
            employee.setRole(dto.role());
        }

        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public List<EmployeeResponseDTO> getAllEmployees(){
        Employee loggedUser = LoggedUser.get();
        if(loggedUser == null || loggedUser.getRole() != Role.ADMIN){
            throw new EmployeeCreationPermissionException();
        }

        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    @Override
    public List<EmployeeResponseDTO> filterEmployees(
            String name,
            String lastName,
            String dni,
            String email,
            String phoneNumber,
            Role role,
            Boolean deleted
    ){
        Employee loggedUser = LoggedUser.get();
        if(loggedUser == null || loggedUser.getRole() != Role.ADMIN){
            throw new EmployeeCreationPermissionException();
        }

        Specification<Employee> spec = EmployeeSpecification.filterBy(name, lastName, dni, email, phoneNumber, role, deleted);

        List<Employee> employees = employeeRepository.findAll(spec);

        return employees.stream().map(employeeMapper::toDTO).collect(Collectors.toList());
    }

}