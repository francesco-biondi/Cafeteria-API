package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.controller.helper.SortUtils;
import com.progra3.cafeteria_api.model.dto.AuditRequestDTO;
import com.progra3.cafeteria_api.model.dto.AuditResponseDTO;
import com.progra3.cafeteria_api.service.port.IAuditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/audits")
public class AuditController {

    private final IAuditService auditService;

    private final SortUtils sortUtils;

    @PostMapping
    public ResponseEntity<AuditResponseDTO> createAudit(@Valid @RequestBody AuditRequestDTO dto){
        AuditResponseDTO auditResponseDTO = auditService.create(dto);
        return ResponseEntity.created(URI.create("/api/audit/" + auditResponseDTO.id()))
                .body(auditResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<AuditResponseDTO>> getAudits(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "closeTime,asc") String sort
    ){
        Pageable pageable = PageRequest.of(page, size, sortUtils.buildSort(sort));
        Page<AuditResponseDTO> audits = auditService.getAudits(startDate, endDate, pageable);
        return ResponseEntity.ok(audits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditResponseDTO> getAuditById(@PathVariable Long id){
        return ResponseEntity.ok(auditService.getById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AuditResponseDTO> finalizeAudit(@PathVariable Long id){
        return ResponseEntity.ok(auditService.finalize(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AuditResponseDTO> cancelAudit(@PathVariable Long id) {
        return ResponseEntity.ok(auditService.cancel(id));
    }
}