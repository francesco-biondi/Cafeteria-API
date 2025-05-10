package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existByCuit (String cuit);
    Optional<Supplier> findByCuit (String cuit);
}