package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee>{
    Optional<Employee> findByEmail(String email);
    boolean existsByRole(Role role);
    boolean existsByDni(String dni);
    Employee findByDni(String dni);
}
