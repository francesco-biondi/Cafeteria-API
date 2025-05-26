package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {
}
