package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.AuditRequestDTO;
import com.progra3.cafeteria_api.model.dto.AuditResponseDTO;
import com.progra3.cafeteria_api.model.entity.Audit;
import com.progra3.cafeteria_api.model.enums.AuditStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuditMapper {
    private final ExpenseMapper expenseMapper;
    private final OrderMapper orderMapper;

    public AuditResponseDTO toDTO (Audit audit){
        return AuditResponseDTO.builder()
                .startTime(audit.getStartTime())
                .closeTime(audit.getCloseTime())
                .initialCash(audit.getInitialCash())
                .orders(Optional.ofNullable(audit.getOrders())
                        .map(orderMapper::toDTOList)
                        .orElse(List.of()))
                .expenses(Optional.ofNullable(audit.getExpenses())
                        .map(expenseMapper::toDTOList)
                        .orElse(List.of()))
                .totalExpensed(audit.getTotalExpensed())
                .total(audit.getTotal())
                .balanceGap(audit.getBalanceGap())
                .deleted(audit.getDeleted())
                .build();
    }

    public Audit toEntity (AuditRequestDTO dto){
        return Audit.builder()
                .initialCash(dto.initialCash())
                .auditStatus(AuditStatus.IN_PROGRESS)
                .build();
    }
}