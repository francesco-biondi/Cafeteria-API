package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByCuitAndBusiness_Id(String cuit, Long businessId);
    Optional<Supplier> findByIdAndBusiness_Id(Long id, Long businessId);
    List<Supplier> findByBusiness_Id(Long businessId);
}