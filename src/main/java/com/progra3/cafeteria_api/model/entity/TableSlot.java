package com.progra3.cafeteria_api.model.entity;

import com.progra3.cafeteria_api.model.enums.TableSlotStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "table_slots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "tableSlot", cascade = CascadeType.ALL)
    private List<Order> orders;

    @Column(nullable = false)
    private TableSlotStatus status;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;
}
