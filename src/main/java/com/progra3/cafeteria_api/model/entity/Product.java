package com.progra3.cafeteria_api.model.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "category")
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "description", nullable = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String description;

    @Column(name = "price", nullable = false)
    @NotNull
    @Positive
    private Double price;

    @Column(name = "cost", nullable = false)
    @NotNull
    @Positive
    private Double cost;

    @Column(name = "stock", nullable = false)
    @NotNull
    @Min(0)
    private Integer stock;
}