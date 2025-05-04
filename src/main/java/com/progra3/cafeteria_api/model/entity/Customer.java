package com.progra3.cafeteria_api.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Customer extends Person {
    private Double discount;
}