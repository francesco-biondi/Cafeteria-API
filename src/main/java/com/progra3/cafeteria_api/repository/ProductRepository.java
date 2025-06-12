package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByBusiness_Id(Long businessId);

    @EntityGraph(attributePaths = {
            "components",
            "components.product"
    })
    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.business.id = :businessId")
    Optional<Product> findByIdAndBusiness_IdWithComponents(@Param("id") Long id,
                                                           @Param("businessId") Long businessId);
}
