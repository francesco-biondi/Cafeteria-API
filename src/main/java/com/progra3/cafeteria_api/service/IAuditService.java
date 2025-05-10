package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.AuditRequestDTO;
import com.progra3.cafeteria_api.model.dto.AuditResponseDTO;
import com.progra3.cafeteria_api.model.entity.Audit;

import java.util.List;

public interface IAuditService {
    AuditResponseDTO createAudit (AuditRequestDTO dto);
    List<AuditResponseDTO> listAudits ();
    AuditResponseDTO finalizeAudit (Long auditId);
    AuditResponseDTO cancelAudit (Long auditId);

    Audit getEntityById (Long auditId);
    Double calculateExpenseTotal (Audit audit);
    Double calculateTotal (Audit audit);
}