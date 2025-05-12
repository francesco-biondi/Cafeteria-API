package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.CustomerNotFoundException;
import com.progra3.cafeteria_api.exception.SupplierNotFoundException;
import com.progra3.cafeteria_api.model.dto.CustomerRequestDTO;
import com.progra3.cafeteria_api.model.dto.CustomerResponseDTO;
import com.progra3.cafeteria_api.model.dto.mapper.CustomerMapper;
import com.progra3.cafeteria_api.model.entity.Customer;
import com.progra3.cafeteria_api.repository.CustomerRepository;
import com.progra3.cafeteria_api.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto) {
         Customer customer = customerMapper.toEntity(dto);

        if (customerRepository.existsById(customer.getId())){
            customer = getEntityById(customer.getId());
            customer.setDeleted(false);
            customerRepository.save(customer);
        }else {
            customerRepository.save(customer);
        }

        return customerMapper.toDTO(customer);
    }


    @Override
    public List<CustomerResponseDTO> listCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toDTO)
                .toList();
    }

    @Override
    public CustomerResponseDTO updateCustomer(CustomerRequestDTO dto) {
        Customer customer = customerMapper.toEntity(dto);

        if (customerRepository.existsById(customer.getId())){
            customerRepository.save(customer);
        }else throw new CustomerNotFoundException(customer.getId());

        return customerMapper.toDTO(customer);
    }

    @Override
    public CustomerResponseDTO deleteCustomer(Long customerId) {
        Customer customer = getEntityById(customerId);
        customer.setDeleted(true);

        return customerMapper.toDTO(customerRepository.save(customer));
    }

    @Override
    public Customer getEntityById (Long customerId) {
        return Optional.ofNullable(customerId)
                .map(customer -> customerRepository.findById(customerId)
                        .orElseThrow(() -> new CustomerNotFoundException(customerId)))
                .orElse(null);
    }

    @Override
    public CustomerResponseDTO getDtoById (Long id){
        return customerMapper.toDTO(getEntityById(id));
    }
}