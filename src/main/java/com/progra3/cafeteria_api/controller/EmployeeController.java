package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.DTO.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.DTO.EmployeeResponseDTO;
import com.progra3.cafeteria_api.service.impl.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<EmployeeResponseDTO> login(@RequestParam String email, @RequestParam String password){
        EmployeeResponseDTO response = employeeService.login(email, password);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody @Valid EmployeeRequestDTO requestDTO){
        EmployeeResponseDTO response = employeeService.createEmployee(requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> deleteEmployee(@PathVariable Long id){
        EmployeeResponseDTO response = employeeService.deleteEmployee(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/init-admin")
    public ResponseEntity<EmployeeResponseDTO> createAdmin(@RequestBody @Valid EmployeeRequestDTO requestDTO){
        EmployeeResponseDTO response = employeeService.createAdmin(requestDTO);
        return ResponseEntity.ok(response);
    }
}
