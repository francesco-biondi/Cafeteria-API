package com.progra3.cafeteria_api.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Data
@SuperBuilder
public class Supplier extends Person{
    private String address;
}
