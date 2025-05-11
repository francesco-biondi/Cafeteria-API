package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existByDni (String dni);
    Optional<Customer> findByDni (String dni);
}