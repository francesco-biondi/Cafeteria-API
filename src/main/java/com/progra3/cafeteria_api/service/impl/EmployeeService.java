package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.customer.CustomerNotFoundException;
import com.progra3.cafeteria_api.exception.user.*;
import com.progra3.cafeteria_api.model.dto.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeUpdateDTO;
import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.model.enums.Role;
import com.progra3.cafeteria_api.model.mapper.EmployeeMapper;
import com.progra3.cafeteria_api.repository.EmployeeRepository;
import com.progra3.cafeteria_api.service.IEmployeeService;
import com.progra3.cafeteria_api.specification.EmployeeSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService{

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public EmployeeResponseDTO createEmployeeOrAdmin(EmployeeRequestDTO dto){
        boolean adminExists = employeeRepository.existsByRole(Role.ADMIN);

        if(!adminExists){
            Employee newAdmin = employeeMapper.toEntity(dto);
            newAdmin.setRole(Role.ADMIN);
            newAdmin.setDeleted(false);

            return employeeMapper.toDTO(employeeRepository.save(newAdmin));
        }

        Employee loggedUser = LoggedUser.get();

        if(loggedUser == null || loggedUser.getRole() != Role.ADMIN){
            throw new EmployeePermissionException();
        }

        Employee employee = employeeMapper.toEntity(dto);

        if(employee.getRole() == Role.ADMIN){
            throw new AdminAlreadyExistsException();
        }

        if (employeeRepository.existsByDni(employee.getDni())){
            employee = employeeRepository.findByDni(employee.getDni());
            if (!employee.getDeleted()){
                throw new EmployeeAlreadyActiveException(employee.getDni());
            }
        }

        employee.setDeleted(false);

        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    public Employee getEntityById (Long employeeId) {
        return Optional.ofNullable(employeeId)
                .map(customer -> employeeRepository.findById(employeeId)
                        .orElseThrow(() -> new CustomerNotFoundException(employeeId)))
                .orElse(null);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO deleteEmployee(Long id){
        Employee loggedUser = LoggedUser.get();
        if(loggedUser == null || loggedUser.getRole() != Role.ADMIN){
            throw new EmployeeCannotBeDeletedException();
        }

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        if(employee.getRole() == Role.ADMIN){
            throw new AdminCannotBeDeletedException();
        }

        employee.setDeleted(true);

        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeUpdateDTO dto){
        Employee loggedUser = LoggedUser.get();
        if(loggedUser == null || loggedUser.getRole() != Role.ADMIN){
            throw new EmployeePermissionException();
        }

        Employee employee = getEntityById(id);
        employeeMapper.updateEmployeeFromDTO(dto, employee);

        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id){
        return employeeMapper.toDTO(getEntityById(id));
    }

    @Override
    @Transactional
    public List<EmployeeResponseDTO> getAllEmployees(){
        Employee loggedUser = LoggedUser.get();
        if(loggedUser == null || loggedUser.getRole() != Role.ADMIN){
            throw new EmployeePermissionException();
        }

        List<Employee> employees = employeeRepository.findAll();
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
        Employee loggedUser = LoggedUser.get();
        if(loggedUser == null || loggedUser.getRole() != Role.ADMIN){
            throw new EmployeePermissionException();
        }

        Specification<Employee> spec = EmployeeSpecification.filterBy(name, lastName, dni, email, phoneNumber, role, deleted);

        List<Employee> employees = employeeRepository.findAll(spec);

        return employees.stream().filter(n -> !n.getDeleted()).map(employeeMapper::toDTO).collect(Collectors.toList());
    }
}