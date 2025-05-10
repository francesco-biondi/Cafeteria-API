package com.progra3.cafeteria_api.model.entity;

import com.progra3.cafeteria_api.model.enums.AuditStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class Audit{
    private long id;
    private LocalDateTime startTime;
    private LocalDateTime closeTime;
    private Double initialCash;
    private List<Order> orders;
    private List<Expense> expenses;
    private AuditStatus auditStatus;
    private Double totalExpensed;
    private Double total;
    private Double balanceGap;
    private Boolean deleted;
}