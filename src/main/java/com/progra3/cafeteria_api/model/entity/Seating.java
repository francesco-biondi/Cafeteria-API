package com.progra3.cafeteria_api.model.entity;

import com.progra3.cafeteria_api.model.enums.SeatingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "seating")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Integer number;

    @OneToMany(mappedBy = "seating", cascade = CascadeType.ALL)
    private List<Order> orders;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatingStatus status;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;
}
