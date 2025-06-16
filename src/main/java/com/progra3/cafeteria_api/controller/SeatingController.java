package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.SeatingRequestDTO;
import com.progra3.cafeteria_api.model.dto.SeatingResponseDTO;
import com.progra3.cafeteria_api.service.port.ISeatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seating")
public class SeatingController {

    private final ISeatingService seatingService;

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<SeatingResponseDTO> createSeating(@RequestBody SeatingRequestDTO dto) {
        SeatingResponseDTO seatingResponseDTO = seatingService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/seating/" + seatingResponseDTO.id()))
                .body(seatingResponseDTO);
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER', 'WAITER')")
    @GetMapping
    public ResponseEntity<List<SeatingResponseDTO>> getAllSeating() {
        return ResponseEntity.ok(seatingService.getAll());
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER', 'WAITER')")
    @GetMapping("/{id}")
    public ResponseEntity<SeatingResponseDTO> getSeatingById(@PathVariable Long id) {
        return ResponseEntity.ok(seatingService.getById(id));
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER', 'WAITER')")
    @GetMapping("/number")
    public ResponseEntity<SeatingResponseDTO> getSeatingByNumber(@RequestParam Integer number) {
        return ResponseEntity.ok(seatingService.getByNumber(number));
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SeatingResponseDTO> updateSeating(@PathVariable Long id,
                                                            @RequestBody SeatingRequestDTO dto) {
        SeatingResponseDTO seatingResponseDTO = seatingService.updateNumber(id, dto);
        return ResponseEntity.ok(seatingResponseDTO);
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<SeatingResponseDTO> deleteSeating(@PathVariable Long id) {

        return ResponseEntity.ok(seatingService.delete(id));
    }
}
