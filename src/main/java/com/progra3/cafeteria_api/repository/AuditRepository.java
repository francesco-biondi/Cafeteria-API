package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
    Optional<Audit> findTopByBusiness_IdOrderByIdDesc(Long business_id);
    List<Audit> findByBusiness_Id(Long business_id);
    Optional<Audit> findByIdAndBusiness_Id(Long id, Long business_id);

    boolean existsByBusiness_Id(Long business_id);
}