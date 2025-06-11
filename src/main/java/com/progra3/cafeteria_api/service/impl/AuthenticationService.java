package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.user.EmployeeDeletedException;
import com.progra3.cafeteria_api.exception.user.EmployeeNotFoundException;
import com.progra3.cafeteria_api.exception.user.InvalidPasswordException;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;
import com.progra3.cafeteria_api.model.dto.LoginRequestDTO;
import com.progra3.cafeteria_api.model.dto.LoginResponseDTO;
import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.model.mapper.EmployeeMapper;
import com.progra3.cafeteria_api.repository.EmployeeRepository;
import com.progra3.cafeteria_api.security.JwtService;
import com.progra3.cafeteria_api.service.IAuthenticationService;
import com.progra3.cafeteria_api.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final BusinessService businessService;
    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional
    public LoginResponseDTO login(LoginRequestDTO dto) {

        Long businessId = businessService.getCurrentBusinessId();

        Employee employee = employeeRepository.findByEmailAndBusiness_Id(dto.email(), businessId)
                .orElseThrow(() -> new EmployeeNotFoundException(dto.email()));

        if (!passwordEncoder.matches(dto.password(), employee.getPassword())) {
            throw new InvalidPasswordException();
        }

        if (Boolean.TRUE.equals(employee.getDeleted())) {
            throw new EmployeeDeletedException();
        }

        CustomUserDetails userDetails = new CustomUserDetails(
                employee.getEmail() + "|" + businessId,
                employee.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + employee.getRole().name())),
                businessId
        );

        String token = jwtService.generateToken(userDetails);

        EmployeeResponseDTO employeeDTO = employeeMapper.toDTO(employee);

        return new LoginResponseDTO(token, employeeDTO);
    }
}