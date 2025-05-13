package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.ExpenseRequestDTO;
import com.progra3.cafeteria_api.model.dto.ExpenseResponseDTO;
import com.progra3.cafeteria_api.model.entity.Expense;

import java.time.LocalDateTime;
import java.util.List;

public interface IExpenseService {
    ExpenseResponseDTO create(ExpenseRequestDTO dto);
    List<ExpenseResponseDTO> getAll();
    ExpenseResponseDTO update(Long expenseId, Double amount);
    ExpenseResponseDTO delete(Long expenseId);

    Expense getEntityById (Long expenseId);
    List<Expense> getByDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
