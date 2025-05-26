package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
    Audit findTopByOrderByIdDesc();
    boolean existsBy();
}