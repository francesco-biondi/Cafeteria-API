package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.service.helper.SortUtils;
import com.progra3.cafeteria_api.model.dto.ExpenseRequestDTO;
import com.progra3.cafeteria_api.model.dto.ExpenseResponseDTO;
import com.progra3.cafeteria_api.model.dto.ExpenseUpdateDTO;
import com.progra3.cafeteria_api.service.port.IExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final IExpenseService expenseService;

    private final SortUtils sortUtils;

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> createExpense(@Valid @RequestBody ExpenseRequestDTO dto) {
        ExpenseResponseDTO responseDTO = expenseService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/expenses/" + responseDTO.id()))
                .body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ExpenseResponseDTO>> getExpenses(
            @RequestParam(required = false) Long supplierId,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateTime,asc") String sort
    ){
        Pageable pageable = PageRequest.of(page, size, sortUtils.buildSort(sort));
        Page<ExpenseResponseDTO> expenses = expenseService.getExpenses(supplierId, minAmount, maxAmount, startDate, endDate, pageable);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(@PathVariable Long id){
        return ResponseEntity.ok(expenseService.getById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseUpdateDTO dto){
        return ResponseEntity.ok(expenseService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}