package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
}