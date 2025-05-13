package com.progra3.cafeteria_api.repository;

import com.progra3.cafeteria_api.model.entity.Seating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatingRepository extends JpaRepository<Seating, Long> {
    Optional<Seating> findByNumber(Integer number);
}
