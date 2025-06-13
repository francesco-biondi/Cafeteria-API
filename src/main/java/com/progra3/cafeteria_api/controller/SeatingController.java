package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.SeatingRequestDTO;
import com.progra3.cafeteria_api.model.dto.SeatingResponseDTO;
import com.progra3.cafeteria_api.service.port.ISeatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seating")
public class SeatingController {

    private final ISeatingService seatingService;

    @PostMapping
    public ResponseEntity<SeatingResponseDTO> createSeating(@RequestBody SeatingRequestDTO dto) {
        SeatingResponseDTO seatingResponseDTO = seatingService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/seating/" + seatingResponseDTO.id()))
                .body(seatingResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<SeatingResponseDTO>> getAllSeating() {
        return ResponseEntity.ok(seatingService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatingResponseDTO> getSeatingById(@PathVariable Long id) {
        return ResponseEntity.ok(seatingService.getById(id));
    }

    @GetMapping("/number")
    public ResponseEntity<SeatingResponseDTO> getSeatingByNumber(@RequestParam Integer number) {
        return ResponseEntity.ok(seatingService.getByNumber(number));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatingResponseDTO> updateSeating(@PathVariable Long id,
                                                            @RequestBody SeatingRequestDTO dto) {
        SeatingResponseDTO seatingResponseDTO = seatingService.updateNumber(id, dto);
        return ResponseEntity.ok(seatingResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SeatingResponseDTO> deleteSeating(@PathVariable Long id) {

        return ResponseEntity.ok(seatingService.delete(id));
    }
}
