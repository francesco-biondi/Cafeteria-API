package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.SupplierNotFoundException;
import com.progra3.cafeteria_api.model.dto.SupplierRequestDTO;
import com.progra3.cafeteria_api.model.dto.SupplierResponseDTO;
import com.progra3.cafeteria_api.model.dto.mapper.SupplierMapper;
import com.progra3.cafeteria_api.model.entity.Supplier;
import com.progra3.cafeteria_api.repository.SupplierRepository;
import com.progra3.cafeteria_api.service.ISupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService implements ISupplierService {
    private final SupplierRepository supplierRepository;

    private final SupplierMapper supplierMapper;

    @Override
    public SupplierResponseDTO create(SupplierRequestDTO dto) {
        Supplier supplier = supplierMapper.toEntity(dto);

        if (supplierRepository.existsById(supplier.getId())){
            supplier = getEntityById(supplier.getId());
            supplier.setDeleted(false);
            supplierRepository.save(supplier);
        }else {
            supplierRepository.save(supplier);
        }

        return supplierMapper.toDTO(supplier);
    }

    @Override
    public List<SupplierResponseDTO> getAll() {
        return supplierRepository.findAll()
                .stream()
                .map(supplierMapper::toDTO)
                .toList();
    }

    @Override
    public SupplierResponseDTO update(SupplierRequestDTO dto){
        Supplier supplier = supplierMapper.toEntity(dto);

        if (supplierRepository.existsById(supplier.getId())){
            supplierRepository.save(supplier);
        }else throw new SupplierNotFoundException(supplier.getId());

        return supplierMapper.toDTO(supplier);
    }

    @Override
    public SupplierResponseDTO delete(Long supplierId){
        Supplier supplier = getEntityById(supplierId);
        supplier.setDeleted(true);

        return supplierMapper.toDTO(supplierRepository.save(supplier));
    }

    @Override
    public Supplier getEntityById (Long supplierId){
        return supplierRepository.findById(supplierId).orElseThrow(() -> new SupplierNotFoundException(supplierId));
    }

    @Override
    public SupplierResponseDTO getDtoById (Long supplierId){
        return supplierMapper.toDTO(getEntityById(supplierId));
    }
}