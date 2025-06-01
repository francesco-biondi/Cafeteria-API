package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.seating.SeatingAlreadyExistsException;
import com.progra3.cafeteria_api.exception.seating.SeatingModificationNotAllowed;
import com.progra3.cafeteria_api.exception.seating.SeatingNotFoundException;
import com.progra3.cafeteria_api.model.dto.SeatingRequestDTO;
import com.progra3.cafeteria_api.model.dto.SeatingResponseDTO;
import com.progra3.cafeteria_api.model.mapper.SeatingMapper;
import com.progra3.cafeteria_api.model.entity.Seating;
import com.progra3.cafeteria_api.model.enums.OrderStatus;
import com.progra3.cafeteria_api.model.enums.SeatingStatus;
import com.progra3.cafeteria_api.repository.SeatingRepository;
import com.progra3.cafeteria_api.service.ISeatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeatingService implements ISeatingService {
    private final SeatingRepository seatingRepository;

    private final SeatingMapper seatingMapper;

    @Override
    public SeatingResponseDTO create(SeatingRequestDTO seatingRequestDTO) {
        Seating seating = seatingMapper.toEntity(seatingRequestDTO);
        seatingRepository.findByNumber(seating.getNumber())
                .ifPresent(s -> {
                    throw new SeatingAlreadyExistsException(seating.getNumber());
                });
        return seatingMapper.toDTO(seatingRepository.save(seating));
    }

    @Override
    public SeatingResponseDTO getById(Long seatingId) {
        return seatingMapper.toDTO(getEntityById(seatingId));
    }

    @Override
    public Seating getEntityById(Long id) {
        return Optional.ofNullable(id)
                .map(customer -> seatingRepository.findById(id)
                        .orElseThrow(() -> new SeatingNotFoundException(id)))
                .orElse(null);
    }

    @Override
    public List<SeatingResponseDTO> getAll() {
        return seatingRepository.findAll()
                .stream()
                .map(seatingMapper::toDTO)
                .toList();
    }

    @Override
    public SeatingResponseDTO updateNumber(Long id, SeatingRequestDTO dto) {
        Seating seating = getEntityById(id);

        validateSeating(seating);

        seating.setNumber(dto.number());

        return seatingMapper.toDTO(seatingRepository.save(seating));
    }

    @Override
    public void updateStatus(Seating seating, OrderStatus status) {

        validateSeating(seating);

        SeatingStatus newSeatingStatus = switch (status) {
            case BILLED -> SeatingStatus.BILLING;
            case FINALIZED, CANCELED -> SeatingStatus.FREE;
            default -> null;
        };

        seating.setStatus(newSeatingStatus);

        seatingRepository.save(seating);
    }

    @Override
    public SeatingResponseDTO delete(Long id) {
        Seating seating = getEntityById(id);
        seating.setStatus(null);
        seating.setDeleted(true);
        seating.setNumber(null);

        return seatingMapper.toDTO(seatingRepository.save(seating));
    }

    @Override
    public SeatingResponseDTO getByNumber(Integer number) {
        return seatingMapper.toDTO(seatingRepository.findByNumber(number)
                .orElseThrow(() -> new SeatingNotFoundException(number)));
    }

    private void validateSeating(Seating seating) {
        if (seating.getDeleted()) {
            throw new SeatingModificationNotAllowed("Seating is deleted and cannot be modified.");
        }
    }
}
