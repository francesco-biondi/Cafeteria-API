package com.progra3.cafeteria_api.service.port;

import com.progra3.cafeteria_api.model.dto.AuditRequestDTO;
import com.progra3.cafeteria_api.model.dto.AuditResponseDTO;
import com.progra3.cafeteria_api.model.entity.Audit;

import java.util.List;

public interface IAuditService {
    AuditResponseDTO create(AuditRequestDTO dto);
    List<AuditResponseDTO> getAll();
    AuditResponseDTO getById(Long auditId);
    AuditResponseDTO finalize(Long auditId);
    AuditResponseDTO cancel(Long auditId);

    Audit getEntityById (Long auditId);
}