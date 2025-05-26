package com.progra3.cafeteria_api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Customer extends Person {
    public static final Integer NO_DISCOUNT = 0;

    @Column
    private Integer discount = NO_DISCOUNT;
}