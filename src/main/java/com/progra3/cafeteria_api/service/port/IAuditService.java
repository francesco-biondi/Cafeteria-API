package com.progra3.cafeteria_api.service.port;

import com.progra3.cafeteria_api.model.dto.AuditRequestDTO;
import com.progra3.cafeteria_api.model.dto.AuditResponseDTO;
import com.progra3.cafeteria_api.model.entity.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface IAuditService {
    AuditResponseDTO create(AuditRequestDTO dto);
    Page<AuditResponseDTO> getAudits(LocalDate startDate, LocalDate endDate, Pageable pageable);
    AuditResponseDTO getById(Long auditId);
    AuditResponseDTO finalize(Long auditId);
    AuditResponseDTO cancel(Long auditId);

    Audit getEntityById (Long auditId);
}