package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.TableSlotRequestDTO;
import com.progra3.cafeteria_api.model.dto.TableSlotResponseDTO;
import com.progra3.cafeteria_api.service.impl.TableSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/table-slots")
@RequiredArgsConstructor
public class TableSlotController {

    private final TableSlotService tableSlotService;

    @PostMapping
    public ResponseEntity<TableSlotResponseDTO> create(@RequestBody TableSlotRequestDTO dto) {
        TableSlotResponseDTO tableSlotResponseDTO = tableSlotService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/table-slots/" + tableSlotResponseDTO.id()))
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

    @PutMapping("/{id}")
    public ResponseEntity<TableSlotResponseDTO> updateTableSlot(@RequestBody TableSlotRequestDTO dto, @PathVariable Long id) {
        TableSlotResponseDTO tableSlotResponseDTO = tableSlotService.updateName(id, dto);
        return ResponseEntity.ok(tableSlotResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tableSlotService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<TableSlotResponseDTO> getByName(@PathVariable String name) {
        return ResponseEntity.ok(tableSlotService.getByName(name));
    }
}
