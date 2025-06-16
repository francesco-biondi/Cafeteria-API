package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.SupplierRequestDTO;
import com.progra3.cafeteria_api.model.dto.SupplierResponseDTO;
import com.progra3.cafeteria_api.model.dto.SupplierUpdateDTO;
import com.progra3.cafeteria_api.service.port.ISupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final ISupplierService supplierService;

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<SupplierResponseDTO> createSupplier(@Valid @RequestBody SupplierRequestDTO dto) {
        SupplierResponseDTO responseDTO = supplierService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/suppliers/" + responseDTO.id()))
                .body(responseDTO);
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER')")
    @GetMapping
    public ResponseEntity<List<SupplierResponseDTO>> getAllSuppliers(){
        return ResponseEntity.ok(supplierService.getAll());
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER')")
    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> getSupplierById(@PathVariable Long id){
        return ResponseEntity.ok(supplierService.getById(id));
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> updateSupplier(@PathVariable Long id, @Valid @RequestBody SupplierUpdateDTO dto){
        return ResponseEntity.ok(supplierService.update(id, dto));
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}