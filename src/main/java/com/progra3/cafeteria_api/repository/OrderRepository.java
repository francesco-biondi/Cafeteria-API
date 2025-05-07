package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.exception.OrderNotFoundException;
import com.progra3.cafeteria_api.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByEmployeeId(Long employeeId) throws OrderNotFoundException;
    List<Order> findByCustomerId(Long customerId) throws OrderNotFoundException;
    List<Order> findByTableId(Long tableId) throws OrderNotFoundException;
}
