package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.CustomerRequestDTO;
import com.progra3.cafeteria_api.model.dto.CustomerResponseDTO;
import com.progra3.cafeteria_api.model.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    CustomerResponseDTO createCustomer (CustomerRequestDTO dto);
    List<CustomerResponseDTO> listCustomers ();
    CustomerResponseDTO updateCustomer (CustomerRequestDTO dto);
    CustomerResponseDTO deleteCustomer (Long customerId);

    Customer getEntityById (Long customerId);
    CustomerResponseDTO getDtoById (Long customerId);
}