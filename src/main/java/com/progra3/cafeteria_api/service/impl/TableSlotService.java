package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.TableNotFoundException;
import com.progra3.cafeteria_api.model.dto.TableSlotRequestDTO;
import com.progra3.cafeteria_api.model.dto.TableSlotResponseDTO;
import com.progra3.cafeteria_api.model.dto.mapper.TableSlotMapper;
import com.progra3.cafeteria_api.model.entity.TableSlot;
import com.progra3.cafeteria_api.model.enums.TableSlotStatus;
import com.progra3.cafeteria_api.repository.TableSlotRepository;
import com.progra3.cafeteria_api.service.ITableSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableSlotService implements ITableSlotService {
    private final TableSlotRepository tableSlotRepository;

    private final TableSlotMapper tableSlotMapper;

    @Override
    public TableSlotResponseDTO create(TableSlotRequestDTO tableSlotRequestDTO) {
        TableSlot tableSlot = TableSlot.builder()
                .name(tableSlotRequestDTO.name())
                .status(TableSlotStatus.FREE)
                .build();

        return tableSlotMapper.toDTO(tableSlotRepository.save(tableSlot));
    }

    @Override
    public TableSlotResponseDTO getById(Long id) {
        return tableSlotMapper.toDTO(getEntityById(id));
    }

    @Override
    public TableSlot getEntityById(Long id) {
        return tableSlotRepository.findById(id)
                .orElseThrow(() -> new TableNotFoundException(id));
    }

    @Override
    public List<TableSlotResponseDTO> getAll() {
        return tableSlotRepository.findAll()
                .stream()
                .map(tableSlotMapper::toDTO)
                .toList();
    }

    @Override
    public TableSlotResponseDTO updateName(Long id, TableSlotRequestDTO tableSlotRequestDTO) {
        TableSlot tableSlot = getEntityById(id);
        tableSlot.setName(tableSlotRequestDTO.name());

        return tableSlotMapper.toDTO(tableSlotRepository.save(tableSlot));
    }

    @Override
    public void updateStatus(Long id, TableSlotStatus status) {
        TableSlot tableSlot = getEntityById(id);
        tableSlot.setStatus(status);
        tableSlotRepository.save(tableSlot);
    }

    @Override
    public void delete(Long id) {
        TableSlot tableSlot = getEntityById(id);
        tableSlot.setDeleted(true);

        tableSlotRepository.save(tableSlot);
    }

    @Override
    public TableSlotResponseDTO getByName(String name) {
        return tableSlotMapper.toDTO(tableSlotRepository.findByName(name)
                .orElseThrow(() -> new TableNotFoundException(name)));
    }


}
