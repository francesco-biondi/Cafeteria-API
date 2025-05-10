package com.progra3.cafeteria_api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "customer")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Customer extends Person {
    @Column()
    private Double discount;
}