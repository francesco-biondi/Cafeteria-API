package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.ExpenseRequestDTO;
import com.progra3.cafeteria_api.model.dto.ExpenseResponseDTO;
import com.progra3.cafeteria_api.service.IExpenseService;
import com.progra3.cafeteria_api.service.impl.ExpenseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final IExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> createExpense(@Valid @RequestBody ExpenseRequestDTO dto) {
        ExpenseResponseDTO responseDTO = expenseService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/expenses/" + responseDTO.id()))
                .body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> getAllExpense(){
        return ResponseEntity.ok(expenseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> getExpenseById (@PathVariable Long id){
        return ResponseEntity.ok(expenseService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense (@PathVariable Long id, @Valid @RequestBody ExpenseRequestDTO dto){
        return ResponseEntity.ok(expenseService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense (@PathVariable Long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}