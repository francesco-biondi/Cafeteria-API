package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.TableSlotRequestDTO;
import com.progra3.cafeteria_api.model.dto.TableSlotResponseDTO;
import com.progra3.cafeteria_api.model.entity.TableSlot;
import com.progra3.cafeteria_api.model.enums.TableSlotStatus;

import java.util.List;

public interface ITableSlotService {
    TableSlotResponseDTO create(TableSlotRequestDTO tableSlotRequestDTO);
    TableSlotResponseDTO getById(Long id);
    TableSlot getEntityById(Long id);
    List<TableSlotResponseDTO> getAll();
    TableSlotResponseDTO update(Long id, TableSlotRequestDTO tableSlotRequestDTO);
    void updateStatus(Long id, TableSlotStatus status);
    void delete(Long id);
    TableSlotResponseDTO getByName(String name);
}
