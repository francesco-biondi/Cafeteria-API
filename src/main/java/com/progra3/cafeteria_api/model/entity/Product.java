package com.progra3.cafeteria_api.model.entity;

import com.progra3.cafeteria_api.model.enums.CompositionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column
    private Double cost;

    @Column
    private Integer stock;

    @Column(nullable = false)
    private Boolean deleted = false;

    @OneToMany(mappedBy = "parentProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductComponent> components = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "groups_by_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "product_group_id")
    )
    private List<ProductGroup> productGroups = new ArrayList<>();

    @Column(nullable = false)
    private Boolean composite = false;

    @Column(name= "composition_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CompositionType compositionType = CompositionType.NONE;

    @Column(name = "control_stock", nullable = false)
    private Boolean controlStock = false;
}