package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.supplier.SupplierNotFoundException;
import com.progra3.cafeteria_api.model.dto.SupplierRequestDTO;
import com.progra3.cafeteria_api.model.dto.SupplierResponseDTO;
import com.progra3.cafeteria_api.model.dto.SupplierUpdateDTO;
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

        if (dto.cuit() != null){
            if (supplierRepository.existsByCuit(supplier.getCuit())){
                supplier = supplierRepository.findByCuit(supplier.getCuit());
            }
        }
        supplier.setDeleted(false);
        supplierRepository.save(supplier);

        return supplierMapper.toDTO(supplier);
    }

    @Override
    public List<SupplierResponseDTO> getAll() {
        return supplierRepository.findAll()
                .stream()
                .filter(n -> !n.getDeleted())
                .map(supplierMapper::toDTO)
                .toList();
    }

    @Override
    public SupplierResponseDTO getById (Long supplierId){
        Supplier supplier = getEntityById(supplierId);
        if (supplier.getDeleted()){
            throw new SupplierNotFoundException(supplierId);
        }
        return supplierMapper.toDTO(supplier);
    }

    @Override
    public SupplierResponseDTO update(Long supplierId, SupplierUpdateDTO dto) {
        if (!supplierRepository.existsById(supplierId)) throw new SupplierNotFoundException(supplierId);

        Supplier supplier = getEntityById(supplierId);
        supplier = supplierMapper.toEntity(dto, supplier);
        supplierRepository.save(supplier);

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