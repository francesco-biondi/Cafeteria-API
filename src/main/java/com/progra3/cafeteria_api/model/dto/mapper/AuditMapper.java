package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.AuditRequestDTO;
import com.progra3.cafeteria_api.model.dto.AuditResponseDTO;
import com.progra3.cafeteria_api.model.dto.TODO.OrderMapper;
import com.progra3.cafeteria_api.model.entity.Audit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class AuditMapper {
    private ExpenseMapper expenseMapper;
    private OrderMapper orderMapper;

    public AuditResponseDTO toDTO (Audit audit){
        return AuditResponseDTO.builder()
                .startTime(audit.getStartTime())
                .closeTime(audit.getCloseTime())
                .initialCash(audit.getInitialCash())
                .orders(audit.getOrders()
                        .stream()
                        .map(orderMapper::toDTO)
                        .toList())
                .expenses(audit.getExpenses()
                        .stream()
                        .map(expenseMapper::toDTO)
                        .toList())
                .build();
    }

    public Audit toEntity (AuditRequestDTO dto){
        return Audit.builder()
                .startTime(dto.startTime())
                .initialCash(dto.initialCash())
                .build();
    }
}