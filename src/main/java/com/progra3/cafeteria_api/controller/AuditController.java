package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.AuditRequestDTO;
import com.progra3.cafeteria_api.model.dto.AuditResponseDTO;
import com.progra3.cafeteria_api.service.IAuditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/audits")
@RequiredArgsConstructor
public class AuditController {

    private final IAuditService auditService;

    @PostMapping
    public ResponseEntity<AuditResponseDTO> createAudit (@Valid @RequestBody AuditRequestDTO dto){
        AuditResponseDTO auditResponseDTO = auditService.create(dto);
        return ResponseEntity.created(URI.create("/api/audit/" + auditResponseDTO.id()))
                .body(auditResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<AuditResponseDTO>> getAllAudits (){
        return ResponseEntity.ok(auditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditResponseDTO> getAuditById (@PathVariable Long id){
        return ResponseEntity.ok(auditService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AuditResponseDTO> cancelAudit (@PathVariable Long id) {
        return ResponseEntity.ok(auditService.cancel(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AuditResponseDTO> finalizeAudit (@PathVariable Long id){
        return ResponseEntity.ok(auditService.finalize(id));
    }
}