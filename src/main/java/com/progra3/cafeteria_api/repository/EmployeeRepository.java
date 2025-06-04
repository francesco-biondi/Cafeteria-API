package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.model.enums.Role;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee>{
    Optional<Employee> findByIdAndBusiness_Id(Long id, Long businessId);
    List<Employee> findByBusiness_Id(Long businessId);
    Optional<Employee> findByEmailAndBusiness_Id(String email, Long businessId);
    Employee findByDniAndBusiness_Id(String dni, Long businessId);

    boolean existsByRoleAndBusiness_Id(Role role, Long businessId);
    boolean existsByDniAndBusiness_Id(String dni, Long businessId);
}
