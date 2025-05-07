package com.progra3.cafeteria_api.model.entity;

import com.progra3.cafeteria_api.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    public static final double NO_DISCOUNT = 0.0;
    public static final double ZERO_AMOUNT = 0.0;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Item> items;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "table_id")
    private TableSlot tableSlot;

    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime time;
    @Column(nullable = false)
    private Double discount = NO_DISCOUNT;
    @Column(name = "people_count", nullable = false)
    private Integer peopleCount;
    @Column(nullable = false)
    private OrderStatus status;
    @Column(nullable = false)
    private Double subtotal = ZERO_AMOUNT;
    @Column(nullable = false)
    private Double total = ZERO_AMOUNT;
}
