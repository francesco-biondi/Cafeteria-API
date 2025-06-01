package com.progra3.cafeteria_api.model.mapper;

import com.progra3.cafeteria_api.model.dto.AddressResponseDTO;
import com.progra3.cafeteria_api.model.dto.SupplierRequestDTO;
import com.progra3.cafeteria_api.model.dto.SupplierResponseDTO;
import com.progra3.cafeteria_api.model.dto.SupplierUpdateDTO;
import com.progra3.cafeteria_api.model.entity.Address;
import com.progra3.cafeteria_api.model.entity.Supplier;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-01T13:50:36-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class SupplierMapperImpl implements SupplierMapper {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public SupplierResponseDTO toDTO(Supplier supplier) {
        if ( supplier == null ) {
            return null;
        }

        Long id = null;
        String legalName = null;
        String tradeName = null;
        String cuit = null;
        String phoneNumber = null;
        String email = null;
        AddressResponseDTO address = null;
        Boolean deleted = null;

        id = supplier.getId();
        legalName = supplier.getLegalName();
        tradeName = supplier.getTradeName();
        cuit = supplier.getCuit();
        phoneNumber = supplier.getPhoneNumber();
        email = supplier.getEmail();
        address = addressMapper.toDTO( supplier.getAddress() );
        deleted = supplier.getDeleted();

        SupplierResponseDTO supplierResponseDTO = new SupplierResponseDTO( id, legalName, tradeName, cuit, phoneNumber, email, address, deleted );

        return supplierResponseDTO;
    }

    @Override
    public Supplier toEntity(SupplierRequestDTO supplierRequestDTO) {
        if ( supplierRequestDTO == null ) {
            return null;
        }

        Supplier supplier = new Supplier();

        supplier.setLegalName( supplierRequestDTO.legalName() );
        supplier.setTradeName( supplierRequestDTO.tradeName() );
        supplier.setCuit( supplierRequestDTO.cuit() );
        supplier.setPhoneNumber( supplierRequestDTO.phoneNumber() );
        supplier.setEmail( supplierRequestDTO.email() );
        supplier.setAddress( addressMapper.toEntity( supplierRequestDTO.address() ) );

        return supplier;
    }

    @Override
    public void updateSupplierFromDTO(SupplierUpdateDTO supplierRequestDTO, Supplier supplier) {
        if ( supplierRequestDTO == null ) {
            return;
        }

        if ( supplierRequestDTO.legalName() != null ) {
            supplier.setLegalName( supplierRequestDTO.legalName() );
        }
        if ( supplierRequestDTO.tradeName() != null ) {
            supplier.setTradeName( supplierRequestDTO.tradeName() );
        }
        if ( supplierRequestDTO.cuit() != null ) {
            supplier.setCuit( supplierRequestDTO.cuit() );
        }
        if ( supplierRequestDTO.phoneNumber() != null ) {
            supplier.setPhoneNumber( supplierRequestDTO.phoneNumber() );
        }
        if ( supplierRequestDTO.email() != null ) {
            supplier.setEmail( supplierRequestDTO.email() );
        }
        if ( supplierRequestDTO.address() != null ) {
            if ( supplier.getAddress() == null ) {
                supplier.setAddress( new Address() );
            }
            addressMapper.updateAddressFromDTO( supplierRequestDTO.address(), supplier.getAddress() );
        }
    }
}
