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
    Optional<Order> findByIdAndBusiness_Id(Long orderId, Long businessId);

    List<Order> findByBusiness_Id(Long id);

    List<Order> findByEmployee_IdAndBusiness_Id(Long employeeId, Long businessId);

    List<Order> findByCustomer_IdAndBusiness_Id(Long customerId, Long businessId);

    Optional<Order> findBySeating_IdAndStatusAndBusiness_Id(Long seatingId, OrderStatus orderStatus, Long businessId);

    List<Order> findByDateTimeBetweenAndBusiness_Id(LocalDateTime start, LocalDateTime end, Long businessId);
}
