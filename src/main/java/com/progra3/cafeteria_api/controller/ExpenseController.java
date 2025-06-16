package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.ExpenseRequestDTO;
import com.progra3.cafeteria_api.model.dto.ExpenseResponseDTO;
import com.progra3.cafeteria_api.model.dto.ExpenseUpdateDTO;
import com.progra3.cafeteria_api.service.port.IExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final IExpenseService expenseService;

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER')")
    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> createExpense(@Valid @RequestBody ExpenseRequestDTO dto) {
        ExpenseResponseDTO responseDTO = expenseService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/expenses/" + responseDTO.id()))
                .body(responseDTO);
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER')")
    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> getAllExpense(){
        return ResponseEntity.ok(expenseService.getAll());
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER')")
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(@PathVariable Long id){
        return ResponseEntity.ok(expenseService.getById(id));
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER')")
    @PatchMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseUpdateDTO dto){
        return ResponseEntity.ok(expenseService.update(id, dto));
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}