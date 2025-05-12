package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.SeatingRequestDTO;
import com.progra3.cafeteria_api.model.dto.SeatingResponseDTO;
import com.progra3.cafeteria_api.model.entity.Seating;
import com.progra3.cafeteria_api.model.enums.OrderStatus;
import com.progra3.cafeteria_api.model.enums.SeatingStatus;

import java.util.List;

public interface ISeatingService {
    SeatingResponseDTO create(SeatingRequestDTO seatingRequestDTO);
    SeatingResponseDTO getById(Long id);
    Seating getEntityById(Long id);
    List<SeatingResponseDTO> getAll();
    SeatingResponseDTO updateNumber(Long id, Integer number);

    SeatingResponseDTO updateStatus(Seating seating, OrderStatus status);
    void delete(Long id);
    SeatingResponseDTO getByNumber(Integer number);
}
