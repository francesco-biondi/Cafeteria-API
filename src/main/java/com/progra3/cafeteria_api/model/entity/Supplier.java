package com.progra3.cafeteria_api.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "suppliers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Supplier{
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "trade_name", nullable = false)
    private String tradeName;

    @Column(name = "cuit")
    private String cuit;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;
}
