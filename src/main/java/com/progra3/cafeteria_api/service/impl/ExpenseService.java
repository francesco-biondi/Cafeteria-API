package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.ExpenseNotFoundException;
import com.progra3.cafeteria_api.exception.InvalidDateException;
import com.progra3.cafeteria_api.model.dto.ExpenseRequestDTO;
import com.progra3.cafeteria_api.model.dto.ExpenseResponseDTO;
import com.progra3.cafeteria_api.model.dto.mapper.ExpenseMapper;
import com.progra3.cafeteria_api.model.entity.Expense;
import com.progra3.cafeteria_api.model.entity.Supplier;
import com.progra3.cafeteria_api.repository.ExpenseRepository;
import com.progra3.cafeteria_api.service.IExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService implements IExpenseService {
    private final ExpenseRepository expenseRepository;

    private final SupplierService supplierService;

    private final ExpenseMapper expenseMapper;

    @Override
    public ExpenseResponseDTO create(ExpenseRequestDTO dto) {
        Supplier supplier = supplierService.getEntityById(dto.supplierId());

        Expense expense = expenseMapper.toEntity(dto, supplier, LocalDateTime.now());

        return expenseMapper.toDTO(expenseRepository.save(expense));
    }

    @Override
    public List<ExpenseResponseDTO> getAll() {
        return expenseRepository.findAll()
                .stream()
                .map(expenseMapper::toDTO)
                .toList();
    }

    @Override
    public ExpenseResponseDTO update(Long expenseId, Double amount) {
        Expense expense = getEntityById(expenseId);
        expense.setAmount(amount);

        return expenseMapper.toDTO(expenseRepository.save(expense));
    }

    @Override
    public ExpenseResponseDTO delete(Long expenseId) {
        Expense expense = getEntityById(expenseId);
        expense.setDeleted(true);

        return expenseMapper.toDTO(expenseRepository.save(expense));
    }

    @Override
    public Expense getEntityById (Long expenseId){
        return expenseRepository.findById(expenseId).orElseThrow(() -> new ExpenseNotFoundException(expenseId));
    }

    @Override
    public List<Expense> getByDateTimeBetween(LocalDateTime start, LocalDateTime end){
        if (start.isAfter(end)){
            throw new InvalidDateException("Start should be earlier than end");
        }
        return expenseRepository.findByDateTimeBetween(start, end);
    }
}
