package com.progra3.cafeteria_api.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "expense")
@AllArgsConstructor
@Data
@Builder
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "audit_id", nullable = true)
    private Audit audit;

    @Column(name = "supplier", nullable = false)
    private Supplier supplier;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;
}