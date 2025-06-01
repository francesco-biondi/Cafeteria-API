package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.expense.ExpenseNotFoundException;
import com.progra3.cafeteria_api.exception.utilities.InvalidDateException;
import com.progra3.cafeteria_api.exception.supplier.SupplierNotFoundException;
import com.progra3.cafeteria_api.model.dto.ExpenseRequestDTO;
import com.progra3.cafeteria_api.model.dto.ExpenseResponseDTO;
import com.progra3.cafeteria_api.model.dto.ExpenseUpdateDTO;
import com.progra3.cafeteria_api.model.mapper.ExpenseMapper;
import com.progra3.cafeteria_api.model.entity.Expense;
import com.progra3.cafeteria_api.model.entity.Supplier;
import com.progra3.cafeteria_api.repository.ExpenseRepository;
import com.progra3.cafeteria_api.service.IExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService implements IExpenseService {
    private final ExpenseRepository expenseRepository;

    private final SupplierService supplierService;

    private final ExpenseMapper expenseMapper;

    private final Clock clock;

    @Override
    public ExpenseResponseDTO create(ExpenseRequestDTO dto) {
        Supplier supplier = supplierService.getEntityById(dto.supplierId());

        if(supplier.getDeleted()){
            throw new SupplierNotFoundException(supplier.getId());
        }

        Expense expense = expenseMapper.toEntity(dto, supplier);
        expense.setDeleted(false);
        expense.setDateTime(LocalDateTime.now(clock));

        return expenseMapper.toDTO(expenseRepository.save(expense));
    }

    @Override
    public List<ExpenseResponseDTO> getAll() {
        return expenseRepository.findAll()
                .stream()
                .filter(n -> !n.getDeleted())
                .map(expenseMapper::toDTO)
                .toList();
    }

    @Override
    public ExpenseResponseDTO getById(Long expenseId){
        Expense expense = getEntityById(expenseId);
        if (expense.getDeleted()){
            throw new ExpenseNotFoundException(expenseId);
        }
        return expenseMapper.toDTO(expense);
    }

    @Override
    public ExpenseResponseDTO update(Long expenseId, ExpenseUpdateDTO dto) {
        if (!expenseRepository.existsById(expenseId)) throw new SupplierNotFoundException(expenseId);

        Expense expense = getEntityById(expenseId);
        Supplier supplier = supplierService.getEntityById(expense.getSupplier().getId());
        expense = expenseMapper.toEntity(dto, supplier, expense);

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