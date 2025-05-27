package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.user.EmployeeDeletedException;
import com.progra3.cafeteria_api.exception.user.EmployeeNotFoundException;
import com.progra3.cafeteria_api.exception.user.InvalidPasswordException;
import com.progra3.cafeteria_api.exception.user.NoLoggedUserException;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;
import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.model.dto.mapper.EmployeeMapper;
import com.progra3.cafeteria_api.model.dto.LoginRequestDTO;
import com.progra3.cafeteria_api.repository.EmployeeRepository;
import com.progra3.cafeteria_api.service.IAuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService{

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public EmployeeResponseDTO login(LoginRequestDTO dto){

        Employee employee = employeeRepository.findByEmail(dto.email())
                .orElseThrow(() -> new EmployeeNotFoundException(dto.email()));

        if(!employee.getPassword().equals(dto.password())){
            throw new InvalidPasswordException();
        }

        if(employee.getDeleted() == true){
            throw new EmployeeDeletedException();
        }

        LoggedUser.set(employee);
        return employeeMapper.toDTO(employee);
    }

    public void logout(){

        if (LoggedUser.get() == null) {
            throw new NoLoggedUserException();
        }

        LoggedUser.clear();

    }

}
