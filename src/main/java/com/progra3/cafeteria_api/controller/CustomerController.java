package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.CustomerRequestDTO;
import com.progra3.cafeteria_api.model.dto.CustomerResponseDTO;
import com.progra3.cafeteria_api.model.dto.CustomerUpdateDTO;
import com.progra3.cafeteria_api.service.port.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {
    private final ICustomerService customerService;

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER')")
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerRequestDTO dto) {
        CustomerResponseDTO responseDTO = customerService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/customers/" + responseDTO.id()))
                .body(responseDTO);
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER')")
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAll());
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getById(id));
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER')")
    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerUpdateDTO dto){
        return ResponseEntity.ok(customerService.update(id, dto));
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}