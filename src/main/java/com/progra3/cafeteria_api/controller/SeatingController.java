package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.SeatingRequestDTO;
import com.progra3.cafeteria_api.model.dto.SeatingResponseDTO;
import com.progra3.cafeteria_api.service.impl.SeatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/seating")
@RequiredArgsConstructor
public class SeatingController {

    private final SeatingService seatingService;

    @PostMapping
    public ResponseEntity<SeatingResponseDTO> create(@RequestBody SeatingRequestDTO dto) {
        SeatingResponseDTO seatingResponseDTO = seatingService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/seating/" + seatingResponseDTO.id()))
                .body(seatingResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<SeatingResponseDTO>> getAll() {
        return ResponseEntity.ok(seatingService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatingResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(seatingService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatingResponseDTO> updateSeating(@PathVariable Long id,
                                                            @RequestBody SeatingRequestDTO dto) {
        SeatingResponseDTO seatingResponseDTO = seatingService.updateNumber(id, dto);
        return ResponseEntity.ok(seatingResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SeatingResponseDTO> delete(@PathVariable Long id) {

        return ResponseEntity.ok(seatingService.delete(id));
    }

    @GetMapping("/number")
    public ResponseEntity<SeatingResponseDTO> getByNumber(@RequestParam Integer number) {
        return ResponseEntity.ok(seatingService.getByNumber(number));
    }
}
