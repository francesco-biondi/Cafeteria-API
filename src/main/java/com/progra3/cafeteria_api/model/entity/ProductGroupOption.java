package com.progra3.cafeteria_api.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_group_options")
public class ProductGroupOption {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_group_id")
    private ProductGroup productGroup;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer maxQuantity;
    private Double priceIncrease = 0.0;
}