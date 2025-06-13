package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.user.*;
import com.progra3.cafeteria_api.model.dto.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeUpdateDTO;
import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.model.enums.Role;
import com.progra3.cafeteria_api.model.mapper.EmployeeMapper;
import com.progra3.cafeteria_api.repository.EmployeeRepository;
import com.progra3.cafeteria_api.security.BusinessContext;
import com.progra3.cafeteria_api.service.port.IEmployeeService;
import com.progra3.cafeteria_api.specification.EmployeeSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService{

    private final EmployeeRepository employeeRepository;

    private final BusinessContext businessContext;

    private final EmployeeMapper employeeMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto){

        if (dto.role().equals(Role.OWNER)){
            throw new OwnerAlreadyExistsException();
        }

        Employee employee = employeeMapper.toEntity(dto);
        employee.setBusiness(businessContext.getCurrentBusiness());
        employee.setDeleted(false);
        employee.setUsername(employee.getUsername() + "@" + employee.getBusiness().getName().toLowerCase().replace(" ", "_"));
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    public Employee getEntityById (Long employeeId) {
        return Optional.ofNullable(employeeId)
                .map(customer -> employeeRepository.findByIdAndBusiness_Id(employeeId, businessContext.getCurrentBusinessId())
                        .orElseThrow(() -> new EmployeeNotFoundException(employeeId)))
                .orElse(null);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO deleteEmployee(Long id){
        Employee employee = getEntityById(id);
        employee.setDeleted(true);

        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeUpdateDTO dto){
        Employee employee = getEntityById(id);
        employee = employeeMapper.updateEmployeeFromDTO(dto, employee);

        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id){
        return employeeMapper.toDTO(getEntityById(id));
    }

    @Override
    @Transactional
    public List<EmployeeResponseDTO> getAllEmployees(){
        List<Employee> employees = employeeRepository.findByBusiness_Id(businessContext.getCurrentBusinessId());
        return employees.stream()
                .filter(n -> !n.getDeleted())
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
        Specification<Employee> spec = EmployeeSpecification.filterBy(name, lastName, dni, email, phoneNumber, role, deleted, businessContext.getCurrentBusinessId());

        List<Employee> employees = employeeRepository.findAll(spec);

        return employees.stream().filter(n -> !n.getDeleted()).map(employeeMapper::toDTO).toList();
    }
}