package com.progra3.cafeteria_api.security;

import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String emailAndBusinessId) throws UsernameNotFoundException {
        String[] parts = emailAndBusinessId.split("\\|");
        String email = parts[0];
        Long businessId = Long.parseLong(parts[1]);

        Employee employee = employeeRepository.findByEmailAndBusiness_Id(email, businessId)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found"));

        return new CustomUserDetails(
                emailAndBusinessId,
                employee.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + employee.getRole().name())),
                businessId
        );
    }
}
