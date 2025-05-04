package com.progra3.cafeteria_api.model.entity;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class Person {
    private final Long id;
    private String name;
    private String lastName;
    private String dni;
    private String phoneNumber;
    private String email;
}
