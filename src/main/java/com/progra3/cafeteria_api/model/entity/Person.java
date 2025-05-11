package com.progra3.cafeteria_api.model.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name", nullable = false)
    private String lastName;

    @Column(name = "dni", nullable = false)
    private String dni;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;
}
