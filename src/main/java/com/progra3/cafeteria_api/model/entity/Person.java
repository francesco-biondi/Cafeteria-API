package com.progra3.cafeteria_api.model.entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class Person {
    private Long id;
    private String name;
    private String lastName;
    private String dni;
    private String phoneNumber;
    private String email;
}
