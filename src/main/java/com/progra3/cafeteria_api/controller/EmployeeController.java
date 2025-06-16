package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.service.helper.SortUtils;
import com.progra3.cafeteria_api.model.dto.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeUpdateDTO;
import com.progra3.cafeteria_api.service.port.IEmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.progra3.cafeteria_api.model.enums.Role;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final IEmployeeService employeeService;

    private final SortUtils sortUtils;

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody @Valid EmployeeRequestDTO dto) {
        EmployeeResponseDTO response = employeeService.createEmployeeOrAdmin(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeResponseDTO>> getEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Role role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, sortUtils.buildSort(sort));
        Page<EmployeeResponseDTO> employees = employeeService.getEmployees(name, lastName, dni, email, role, pageable);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeUpdateDTO dto){
        EmployeeResponseDTO response = employeeService.updateEmployee(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> deleteEmployee(@PathVariable @NotNull Long id){
        EmployeeResponseDTO response = employeeService.deleteEmployee(id);
        return ResponseEntity.ok(response);
    }
}