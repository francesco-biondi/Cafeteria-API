package com.progra3.cafeteria_api.model.mapper;

import com.progra3.cafeteria_api.model.dto.ExpenseRequestDTO;
import com.progra3.cafeteria_api.model.dto.ExpenseResponseDTO;
import com.progra3.cafeteria_api.model.dto.ExpenseUpdateDTO;
import com.progra3.cafeteria_api.model.entity.Expense;
import com.progra3.cafeteria_api.model.entity.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ExpenseMapper {
    private final SupplierMapper supplierMapper;

    public ExpenseResponseDTO toDTO (Expense expense){
        return ExpenseResponseDTO.builder()
                .id(expense.getId())
                .supplier(supplierMapper.toDTO(expense.getSupplier()))
                .amount(expense.getAmount())
                .comment(expense.getComment())
                .date(expense.getDateTime())
                .deleted(expense.getDeleted())
                .build();
    }

    public Expense toEntity (ExpenseRequestDTO dto, Supplier supplier){
        return Expense.builder()
                .supplier(supplier)
                .amount(dto.amount())
                .comment(dto.comment())
                .build();
    }

    public Expense toEntity (ExpenseUpdateDTO dto, Supplier supplier, Expense existingExpense){
        return Expense.builder()
                .id(existingExpense.getId())
                .supplier(supplier)
                .amount(dto.amount() == null ? existingExpense.getAmount() : dto.amount())
                .comment(isNullOrBlank(dto.comment()) ? existingExpense.getComment() : dto.comment())
                .dateTime(existingExpense.getDateTime())
                .deleted(false)
                .build();
    }

    public List<ExpenseResponseDTO> toDTOList (List<Expense> expenses){
        return expenses.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private boolean isNullOrBlank(String s){
        return s == null || s.trim().isEmpty();
    }
}