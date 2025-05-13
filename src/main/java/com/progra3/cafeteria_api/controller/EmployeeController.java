package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;
import com.progra3.cafeteria_api.service.impl.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    //TODO Eliminar
    @PostMapping("/login")
    public ResponseEntity<EmployeeResponseDTO> login(@NotBlank @RequestBody String email, @NotBlank @RequestBody String password){
        EmployeeResponseDTO response = employeeService.login(email, password);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody @Valid EmployeeRequestDTO requestDTO){
        EmployeeResponseDTO response = employeeService.createEmployee(requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> deleteEmployee(@PathVariable @NotNull Long id){
        EmployeeResponseDTO response = employeeService.deleteEmployee(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/init-admin")
    public ResponseEntity<EmployeeResponseDTO> createAdmin(@RequestBody @Valid EmployeeRequestDTO requestDTO){
        EmployeeResponseDTO response = employeeService.createAdmin(requestDTO);
        return ResponseEntity.ok(response);
    }
}