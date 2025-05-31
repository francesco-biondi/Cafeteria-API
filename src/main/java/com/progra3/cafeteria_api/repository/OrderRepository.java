package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Order;
import com.progra3.cafeteria_api.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByEmployee_Id(Long employeeId);
    List<Order> findByCustomer_Id(Long customerId);
    Optional<Order> findBySeating_IdAndStatus(Long seatingId, OrderStatus orderStatus);
    List<Order> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
