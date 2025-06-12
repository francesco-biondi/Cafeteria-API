package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.SupplierRequestDTO;
import com.progra3.cafeteria_api.model.dto.SupplierResponseDTO;
import com.progra3.cafeteria_api.model.dto.SupplierUpdateDTO;
import com.progra3.cafeteria_api.service.port.ISupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final ISupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierResponseDTO> createSupplier(@Valid @RequestBody SupplierRequestDTO dto) {
        SupplierResponseDTO responseDTO = supplierService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/suppliers/" + responseDTO.id()))
                .body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponseDTO>> getAllSuppliers(){
        return ResponseEntity.ok(supplierService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> getSupplierById(@PathVariable Long id){
        return ResponseEntity.ok(supplierService.getById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> updateSupplier(@PathVariable Long id, @Valid @RequestBody SupplierUpdateDTO dto){
        return ResponseEntity.ok(supplierService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}