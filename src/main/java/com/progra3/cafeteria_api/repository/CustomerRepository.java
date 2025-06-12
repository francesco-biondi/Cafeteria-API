package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByDniAndBusiness_Id(String dni, Long businessId);

    List<Customer> findByBusiness_Id(Long businessId);

    Optional<Customer> findByIdAndBusiness_Id(Long id, Long businessId);

    Customer findByDniAndBusiness_Id(String dni, Long businessId);
}