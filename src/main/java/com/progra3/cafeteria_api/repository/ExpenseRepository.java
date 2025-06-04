package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Optional<Expense> findByIdAndBusiness_Id(Long id, Long businessId);
    List<Expense> findByBusiness_Id(Long businessId);
    List<Expense> findByDateTimeBetweenAndBusiness_Id(LocalDateTime start, LocalDateTime end, Long businessId);

    boolean existsByIdAndBusiness_Id(Long id, Long businessId);
}