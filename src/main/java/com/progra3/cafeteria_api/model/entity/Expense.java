package com.progra3.cafeteria_api.model.entity;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
public class Expense {
    private Long id;
    private Audit audit;
    private Supplier supplier;
    private Double amount;
    private String comment;
    private LocalDateTime date;
    private Boolean deleted;
}