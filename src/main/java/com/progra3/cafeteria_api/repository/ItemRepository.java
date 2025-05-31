package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
     Optional<List<Item>> findByOrder_Id(Long orderId);
}
