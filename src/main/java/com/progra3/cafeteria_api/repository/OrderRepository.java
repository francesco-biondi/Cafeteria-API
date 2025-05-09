package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByEmployeeId(Long employeeId);
    List<Order> findByCustomerId(Long customerId);
    List<Order> findBySeatingId(Long tableId);
}
