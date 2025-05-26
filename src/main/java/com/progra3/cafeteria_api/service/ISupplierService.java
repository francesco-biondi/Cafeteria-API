package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.SupplierRequestDTO;
import com.progra3.cafeteria_api.model.dto.SupplierResponseDTO;
import com.progra3.cafeteria_api.model.dto.SupplierUpdateDTO;
import com.progra3.cafeteria_api.model.entity.Supplier;

import java.util.List;

public interface ISupplierService {
    SupplierResponseDTO create(SupplierRequestDTO dto);
    List<SupplierResponseDTO> getAll();
    SupplierResponseDTO getById(Long supplierId);
    SupplierResponseDTO update(Long supplierId, SupplierUpdateDTO dto);
    SupplierResponseDTO delete(Long supplierId);

    Supplier getEntityById (Long supplierId);
    SupplierResponseDTO getDtoById (Long supplierId);
}
