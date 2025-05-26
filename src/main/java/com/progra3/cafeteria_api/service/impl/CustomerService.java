package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.CustomerActiveException;
import com.progra3.cafeteria_api.exception.CustomerNotFoundException;
import com.progra3.cafeteria_api.model.dto.CustomerRequestDTO;
import com.progra3.cafeteria_api.model.dto.CustomerResponseDTO;
import com.progra3.cafeteria_api.model.dto.CustomerUpdateDTO;
import com.progra3.cafeteria_api.model.dto.mapper.CustomerMapper;
import com.progra3.cafeteria_api.model.entity.Customer;
import com.progra3.cafeteria_api.repository.CustomerRepository;
import com.progra3.cafeteria_api.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponseDTO create(CustomerRequestDTO dto) {
        Customer customer = customerMapper.toEntity(dto);

        if (customerRepository.existsBy()) {
            if (customerRepository.existsByDni(customer.getDni())) {
                customer = customerRepository.findByDni(customer.getDni());
                if (!customer.getDeleted()){
                    throw new CustomerActiveException(customer.getDni());
                }
            }
        }
        if (dto.discount() == null){
            customer.setDiscount(0);
        }
        customer.setDeleted(false);

        return customerMapper.toDTO(customerRepository.save(customer));
    }


    @Override
    public List<CustomerResponseDTO> getAll() {
        return customerRepository.findAll().stream()
                .filter(n -> !n.getDeleted())
                .map(customerMapper::toDTO)
                .toList();
    }

    @Override
    public CustomerResponseDTO getById(Long customerId){
        Customer customer = getEntityById(customerId);
        if (customer.getDeleted()){
            throw new CustomerNotFoundException(customerId);
        }
        return customerMapper.toDTO(customer);
    }


    @Override
    public CustomerResponseDTO update(Long customerId, CustomerUpdateDTO dto) {
        if (!customerRepository.existsById(customerId)) throw new CustomerNotFoundException(customerId);

        Customer customer = getEntityById(customerId);
        customer = customerMapper.toEntity(dto, customer);

        return customerMapper.toDTO(customerRepository.save(customer));
    }

    @Override
    public void delete(Long customerId) {
        Customer customer = getEntityById(customerId);
        customer.setDeleted(true);

        customerMapper.toDTO(customerRepository.save(customer));
    }

    @Override
    public Customer getEntityById (Long customerId) {
        if (customerId == null) return null;
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @Override
    public CustomerResponseDTO getDtoById (Long id){
        return customerMapper.toDTO(getEntityById(id));
    }
}