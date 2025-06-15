package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.user.*;
import com.progra3.cafeteria_api.model.dto.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeUpdateDTO;
import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.model.enums.Role;
import com.progra3.cafeteria_api.model.mapper.EmployeeMapper;
import com.progra3.cafeteria_api.repository.EmployeeRepository;
import com.progra3.cafeteria_api.service.port.IEmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService{

    private final EmployeeRepository employeeRepository;

    private final BusinessService businessService;

    private final EmployeeMapper employeeMapper;

    @Override
    @Transactional
    public EmployeeResponseDTO createEmployeeOrAdmin(EmployeeRequestDTO dto){
        boolean adminExists = employeeRepository.existsByRoleAndBusiness_Id(Role.ADMIN, businessService.getCurrentBusinessId());

        if(!adminExists){
            Employee newAdmin = employeeMapper.toEntity(dto, businessService.getCurrentBusiness());
            newAdmin.setRole(Role.ADMIN);
            newAdmin.setDeleted(false);

            return employeeMapper.toDTO(employeeRepository.save(newAdmin));
        }

        Employee loggedUser = LoggedUser.get();

        if(loggedUser == null || loggedUser.getRole() != Role.ADMIN){
            throw new EmployeePermissionException();
        }

        Employee employee = employeeMapper.toEntity(dto, businessService.getCurrentBusiness());

        if(employee.getRole() == Role.ADMIN){
            throw new AdminAlreadyExistsException();
        }

        if (employeeRepository.existsByDniAndBusiness_Id(employee.getDni(), businessService.getCurrentBusinessId())) {
            employee = employeeRepository.findByDniAndBusiness_Id(employee.getDni(), businessService.getCurrentBusinessId());
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
                .map(customer -> employeeRepository.findByIdAndBusiness_Id(employeeId, businessService.getCurrentBusinessId())
                        .orElseThrow(() -> new EmployeeNotFoundException(employeeId)))
                .orElse(null);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO deleteEmployee(Long id){
        Employee loggedUser = LoggedUser.get();
        if(loggedUser == null || loggedUser.getRole() != Role.ADMIN){
            throw new EmployeeCannotBeDeletedException();
        }

        Employee employee = getEntityById(id);

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
    public Page<EmployeeResponseDTO> getEmployees(String name, String lastName, String dni, String email, Role role, Pageable pageable){
        Employee loggedUser = LoggedUser.get();
        if(loggedUser == null || loggedUser.getRole() != Role.ADMIN){
            throw new EmployeePermissionException();
        }

        Page<Employee> employees = employeeRepository.findByBusiness_Id(
                name,
                lastName,
                dni,
                email,
                role,
                businessService.getCurrentBusinessId(),
                pageable);

        return employees.map(employeeMapper::toDTO);
    }
}