package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.CustomerRequestDTO;
import com.progra3.cafeteria_api.model.dto.CustomerResponseDTO;
import com.progra3.cafeteria_api.model.entity.Customer;

import java.util.List;

public interface ICustomerService {
    CustomerResponseDTO create(CustomerRequestDTO dto);
    List<CustomerResponseDTO> getAll();
    CustomerResponseDTO update(CustomerRequestDTO dto);
    CustomerResponseDTO delete(Long customerId);

    Customer getEntityById (Long customerId);
    CustomerResponseDTO getDtoById (Long customerId);
}