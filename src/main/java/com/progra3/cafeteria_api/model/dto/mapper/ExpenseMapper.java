package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.ExpenseRequestDTO;
import com.progra3.cafeteria_api.model.dto.ExpenseResponseDTO;
import com.progra3.cafeteria_api.model.entity.Expense;
import com.progra3.cafeteria_api.model.entity.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
                .build();
    }

    public Expense toEntity (ExpenseRequestDTO dto, Supplier supplier){
        return Expense.builder()
                .supplier(supplier)
                .amount(dto.amount())
                .comment(dto.comment())
                .build();
    }

    public List<ExpenseResponseDTO> toDTOList (List<Expense> expenses){
        return expenses.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}