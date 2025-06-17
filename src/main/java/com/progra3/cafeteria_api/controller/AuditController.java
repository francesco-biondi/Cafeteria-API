package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.AuditRequestDTO;
import com.progra3.cafeteria_api.model.dto.AuditResponseDTO;
import com.progra3.cafeteria_api.service.port.IAuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/audits")
@Tag(name = "Audits", description = "Operations related to audit lifecycle management")
public class AuditController {

    private final IAuditService auditService;

    @Operation(summary = "Create a new audit", description = "Registers a new audit entry in the system")
    @ApiResponse(responseCode = "201", description = "Audit created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AuditResponseDTO.class)))
    @PostMapping
    public ResponseEntity<AuditResponseDTO> createAudit(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Audit data to create",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AuditRequestDTO.class),
                            examples = @ExampleObject(value = """
                                {
                                  "initialCash": 1000.0
                                }
                                """)
                    )
            )
            @Valid @RequestBody AuditRequestDTO dto) {
        AuditResponseDTO auditResponseDTO = auditService.create(dto);
        return ResponseEntity.created(URI.create("/api/audits/" + auditResponseDTO.id()))
                .body(auditResponseDTO);
    }

    @Operation(summary = "Get all audits", description = "Returns a list of all audit entries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Audit list retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AuditResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<AuditResponseDTO>> getAllAudits() {
        return ResponseEntity.ok(auditService.getAll());
    }

    @Operation(summary = "Get an audit by ID", description = "Retrieves a specific audit entry by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Audit found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuditResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Audit not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<AuditResponseDTO> getAuditById(
            @Parameter(description = "ID of the audit to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(auditService.getById(id));
    }

    @Operation(summary = "Finalize an audit", description = "Marks an audit entry as finalized")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Audit finalized successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuditResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Audit not found", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<AuditResponseDTO> finalizeAudit(
            @Parameter(description = "ID of the audit to finalize") @PathVariable Long id) {
        return ResponseEntity.ok(auditService.finalize(id));
    }

    @Operation(summary = "Cancel an audit", description = "Marks an audit entry as cancelled")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Audit cancelled successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuditResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Audit not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<AuditResponseDTO> cancelAudit(
            @Parameter(description = "ID of the audit to cancel") @PathVariable Long id) {
        return ResponseEntity.ok(auditService.cancel(id));
    }
}