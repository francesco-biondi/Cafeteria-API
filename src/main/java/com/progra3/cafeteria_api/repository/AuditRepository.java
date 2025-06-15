package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
    Optional<Audit> findTopByBusiness_IdOrderByIdDesc(Long business_id);

    @Query("SELECT a FROM Audit a WHERE " +
            "(:startDate IS NULL OR a.closeTime >= :startDate) AND " +
            "(:endDate IS NULL OR a.closeTime <= :endDate) AND " +
            "a.business.id = :businessId AND " +
            "a.deleted = false")
    Page<Audit> findByBusiness_Id(@Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate,
                                    @Param("businessId") Long businessId,
                                    Pageable pageable);

    Optional<Audit> findByIdAndBusiness_Id(Long id, Long business_id);

    boolean existsByBusiness_Id(Long business_id);
}