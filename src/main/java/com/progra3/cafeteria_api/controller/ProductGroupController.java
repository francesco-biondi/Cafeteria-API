package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.ProductGroupOptionRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductGroupOptionResponseDTO;
import com.progra3.cafeteria_api.model.dto.ProductGroupRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductGroupResponseDTO;
import com.progra3.cafeteria_api.service.IProductGroupService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
public class ProductGroupController {

    private final IProductGroupService productGroupService;

    @PostMapping
    public ResponseEntity<ProductGroupResponseDTO> create(@RequestBody @Valid ProductGroupRequestDTO dto) {
        ProductGroupResponseDTO response = productGroupService.createProductGroup(dto);
        return ResponseEntity.created(URI.create("/api/groups/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductGroupResponseDTO>> getAll() {
        return ResponseEntity.ok(productGroupService.getAllProductGroups());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductGroupResponseDTO> getById(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(productGroupService.getProductGroupById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductGroupResponseDTO> update(
            @PathVariable @NotNull Long id,
            @RequestBody @Valid ProductGroupRequestDTO dto) {
        return ResponseEntity.ok(productGroupService.updateProductGroup(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long id) {
        productGroupService.deleteProductGroup(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/options")
    public ResponseEntity<List<ProductGroupOptionResponseDTO>> addOptions(
            @PathVariable Long id,
            @RequestBody List<@Valid ProductGroupOptionRequestDTO> options) {
        return ResponseEntity.ok(productGroupService.addOptions(id, options));
    }

    @GetMapping("/{id}/options")
    public ResponseEntity<List<ProductGroupOptionResponseDTO>> getOptions(@PathVariable Long id) {
        return ResponseEntity.ok(productGroupService.getOptions(id));
    }

    @PutMapping("/{id}/options/{optionId}")
    public ResponseEntity<ProductGroupOptionResponseDTO> updateOption(
            @PathVariable Long id,
            @PathVariable Long optionId,
            @RequestBody @Valid ProductGroupOptionRequestDTO dto) {
        return ResponseEntity.ok(productGroupService.updateOption(id, optionId, dto));
    }

    @DeleteMapping("/{id}/options/{optionId}")
    public ResponseEntity<ProductGroupOptionResponseDTO> removeOption(
            @PathVariable Long id,
            @PathVariable Long optionId) {
        return ResponseEntity.ok(productGroupService.removeOption(id, optionId));
    }
}