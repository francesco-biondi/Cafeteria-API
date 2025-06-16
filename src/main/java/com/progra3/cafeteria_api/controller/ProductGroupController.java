package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.ProductOptionRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductOptionResponseDTO;
import com.progra3.cafeteria_api.model.dto.ProductGroupRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductGroupResponseDTO;
import com.progra3.cafeteria_api.service.port.IProductGroupService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
public class ProductGroupController {

    private final IProductGroupService productGroupService;

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<ProductGroupResponseDTO> createProductGroup(@RequestBody @Valid ProductGroupRequestDTO dto) {
        ProductGroupResponseDTO response = productGroupService.createProductGroup(dto);
        return ResponseEntity.created(URI.create("/api/groups/" + response.id())).body(response);
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER', 'WAITER')")
    @GetMapping
    public ResponseEntity<List<ProductGroupResponseDTO>> getAllProductGroups() {
        return ResponseEntity.ok(productGroupService.getAllProductGroups());
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER', 'WAITER')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductGroupResponseDTO> getProductGroupById(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(productGroupService.getProductGroupById(id));
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductGroupResponseDTO> updateProductGroup(
            @PathVariable @NotNull Long id,
            @RequestBody @Valid ProductGroupRequestDTO dto) {
        return ResponseEntity.ok(productGroupService.updateProductGroup(id, dto));
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductGroup(@PathVariable @NotNull Long id) {
        productGroupService.deleteProductGroup(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PostMapping("/{id}/options")
    public ResponseEntity<List<ProductOptionResponseDTO>> addOptions(
            @PathVariable Long id,
            @RequestBody List<@Valid ProductOptionRequestDTO> options) {
        return ResponseEntity.ok(productGroupService.addProductOptions(id, options));
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER', 'WAITER')")
    @GetMapping("/{id}/options")
    public ResponseEntity<List<ProductOptionResponseDTO>> getOptions(@PathVariable Long id) {
        return ResponseEntity.ok(productGroupService.getProductOptions(id));
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PutMapping("/{id}/options/{optionId}")
    public ResponseEntity<ProductOptionResponseDTO> updateOption(
            @PathVariable Long id,
            @PathVariable Long optionId,
            @RequestBody @Valid ProductOptionRequestDTO dto) {
        return ResponseEntity.ok(productGroupService.updateProductOption(id, optionId, dto));
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @DeleteMapping("/{id}/options/{optionId}")
    public ResponseEntity<ProductOptionResponseDTO> removeOption(
            @PathVariable Long id,
            @PathVariable Long optionId) {
        return ResponseEntity.ok(productGroupService.removeProductOption(id, optionId));
    }
}