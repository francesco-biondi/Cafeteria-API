package com.progra3.cafeteria_api.model.entity.components;

import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductComponent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("FIXED")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class FixedProductComponent extends ProductComponent {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fixed_product_id", nullable = false)
    private Product fixedProduct;
}



