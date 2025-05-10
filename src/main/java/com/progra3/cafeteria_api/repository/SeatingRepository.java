package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Seating;
import com.progra3.cafeteria_api.model.enums.SeatingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatingRepository extends JpaRepository<Seating, Long> {
    Optional<Seating> findByNumber(Integer number);
    List<Seating> findByStatus(SeatingStatus status);
}
