package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByNameContainingIgnoreCase(String name);
    List<Category> findByProductsId(Long productId);
}
