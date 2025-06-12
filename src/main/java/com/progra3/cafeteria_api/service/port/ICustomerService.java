package com.progra3.cafeteria_api.service.port;

import com.progra3.cafeteria_api.model.dto.CustomerRequestDTO;
import com.progra3.cafeteria_api.model.dto.CustomerResponseDTO;
import com.progra3.cafeteria_api.model.dto.CustomerUpdateDTO;
import com.progra3.cafeteria_api.model.entity.Customer;

import java.util.List;

public interface ICustomerService {
    CustomerResponseDTO create(CustomerRequestDTO dto);
    List<CustomerResponseDTO> getAll();
    CustomerResponseDTO getById(Long auditId);
    CustomerResponseDTO update(Long customerId, CustomerUpdateDTO dto);
    void delete(Long customerId);

    Customer getEntityById (Long customerId);
}