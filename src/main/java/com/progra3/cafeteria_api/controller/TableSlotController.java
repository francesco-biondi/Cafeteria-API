package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.TableSlotRequestDTO;
import com.progra3.cafeteria_api.model.dto.TableSlotResponseDTO;
import com.progra3.cafeteria_api.model.enums.TableSlotStatus;
import com.progra3.cafeteria_api.service.impl.TableSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/table-slots")
@RequiredArgsConstructor
public class TableSlotController {

    private final TableSlotService tableSlotService;

    @PostMapping
    public ResponseEntity<TableSlotResponseDTO> create(@RequestBody TableSlotRequestDTO dto) {
        TableSlotResponseDTO tableSlotResponseDTO = tableSlotService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/table-slots/" + tableSlotResponseDTO.getId()))
                .body(tableSlotResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<TableSlotResponseDTO>> getAll() {
        return ResponseEntity.ok(tableSlotService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableSlotResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tableSlotService.getById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<TableSlotResponseDTO> getByName(@PathVariable String name) {
        return ResponseEntity.ok(tableSlotService.getByName(name));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TableSlotResponseDTO> updateTableSlot(@RequestBody TableSlotRequestDTO dto, @PathVariable Long id) {
        TableSlotResponseDTO tableSlotResponseDTO = tableSlotService.update(id, dto);
        return ResponseEntity.ok(tableSlotResponseDTO);
    }

    @PatchMapping("/{id}/occupy")
    public ResponseEntity<Void> occupyTable(@PathVariable Long id) {
        tableSlotService.updateStatus(id, TableSlotStatus.OCCUPIED);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/free")
    public ResponseEntity<Void> freeTable(@PathVariable Long id) {
        tableSlotService.updateStatus(id, TableSlotStatus.FREE);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/billing")
    public ResponseEntity<Void> billingTable(@PathVariable Long id) {
        tableSlotService.updateStatus(id, TableSlotStatus.BILLING);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tableSlotService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
