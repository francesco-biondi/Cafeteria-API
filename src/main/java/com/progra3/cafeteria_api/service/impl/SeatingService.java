package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.SeatingNotFoundException;
import com.progra3.cafeteria_api.model.dto.SeatingRequestDTO;
import com.progra3.cafeteria_api.model.dto.SeatingResponseDTO;
import com.progra3.cafeteria_api.model.dto.mapper.SeatingMapper;
import com.progra3.cafeteria_api.model.entity.Seating;
import com.progra3.cafeteria_api.model.enums.SeatingStatus;
import com.progra3.cafeteria_api.repository.SeatingRepository;
import com.progra3.cafeteria_api.service.ISeatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatingService implements ISeatingService {
    private final SeatingRepository seatingRepository;

    private final SeatingMapper seatingMapper;

    @Override
    public SeatingResponseDTO create(SeatingRequestDTO seatingRequestDTO) {
        Seating seating = Seating.builder()
                .number(seatingRequestDTO.number())
                .status(SeatingStatus.FREE)
                .build();

        return seatingMapper.toDTO(seatingRepository.save(seating));
    }

    @Override
    public SeatingResponseDTO getById(Long id) {
        return seatingMapper.toDTO(getEntityById(id));
    }

    @Override
    public Seating getEntityById(Long id) {
        return seatingRepository.findById(id)
                .orElseThrow(() -> new SeatingNotFoundException(id));
    }

    @Override
    public List<SeatingResponseDTO> getAll() {
        return seatingRepository.findAll()
                .stream()
                .map(seatingMapper::toDTO)
                .toList();
    }

    @Override
    public SeatingResponseDTO updateNumber(Long id, Integer number) {
        Seating seating = getEntityById(id);
        seating.setNumber(number);

        return seatingMapper.toDTO(seatingRepository.save(seating));
    }

    @Override
    public SeatingResponseDTO updateStatus(Long id, SeatingStatus status) {
        Seating seating = getEntityById(id);
        seating.setStatus(status);

        return seatingMapper.toDTO(seatingRepository.save(seating));
    }

    @Override
    public void delete(Long id) {
        Seating seating = getEntityById(id);
        seating.setDeleted(true);

        seatingRepository.save(seating);
    }

    @Override
    public SeatingResponseDTO getByNumber(Integer number) {
        return seatingMapper.toDTO(seatingRepository.findByNumber(number)
                .orElseThrow(() -> new SeatingNotFoundException(number)));
    }
}
