package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.ProductGroupOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductGroupOptionRepository extends JpaRepository<ProductGroupOption, Long> {
}
