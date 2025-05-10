package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.exception.SupplierNotFoundException;
import com.progra3.cafeteria_api.model.dto.SupplierRequestDTO;
import com.progra3.cafeteria_api.model.dto.SupplierResponseDTO;
import com.progra3.cafeteria_api.model.entity.Supplier;

import java.util.List;

public interface ISupplierService {
    SupplierResponseDTO createSupplier (SupplierRequestDTO dto);
    List<SupplierResponseDTO> listSuppliers ();
    SupplierResponseDTO updateSupplier (SupplierRequestDTO dto);
    SupplierResponseDTO deleteSupplier (Long supplierId);

    Supplier getEntityById (Long supplierId);
    SupplierResponseDTO getDtoById (Long supplierId);
}
