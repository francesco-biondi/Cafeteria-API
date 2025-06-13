package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.customer.CustomerAlreadyActiveException;
import com.progra3.cafeteria_api.exception.customer.CustomerNotFoundException;
import com.progra3.cafeteria_api.model.dto.CustomerRequestDTO;
import com.progra3.cafeteria_api.model.dto.CustomerResponseDTO;
import com.progra3.cafeteria_api.model.dto.CustomerUpdateDTO;
import com.progra3.cafeteria_api.model.entity.Customer;
import com.progra3.cafeteria_api.model.mapper.CustomerMapper;
import com.progra3.cafeteria_api.repository.CustomerRepository;
import com.progra3.cafeteria_api.security.BusinessContext;
import com.progra3.cafeteria_api.service.port.ICustomerService;
import com.progra3.cafeteria_api.service.helper.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    private final BusinessContext businessContext;

    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponseDTO create(CustomerRequestDTO dto) {
        Customer customer = customerMapper.toEntity(dto);
        customer.setBusiness(businessContext.getCurrentBusiness());

        if (customerRepository.existsByDniAndBusiness_Id(customer.getDni(), businessContext.getCurrentBusinessId())) {
            customer = customerRepository.findByDniAndBusiness_Id(customer.getDni(), businessContext.getCurrentBusinessId());
            if (!customer.getDeleted()) {
                throw new CustomerAlreadyActiveException(customer.getDni());
            }
        }
        if (dto.discount() == null) customer.setDiscount(Constant.NO_DISCOUNT);
        customer.setDeleted(false);

        return customerMapper.toDTO(customerRepository.save(customer));
    }


    @Override
    public List<CustomerResponseDTO> getAll() {
        return customerRepository.findByBusiness_Id(businessContext.getCurrentBusinessId())
                .stream()
                .filter(n -> !n.getDeleted())
                .map(customerMapper::toDTO)
                .toList();
    }

    @Override
    public CustomerResponseDTO getById(Long customerId) {
        Customer customer = getEntityById(customerId);
        if (customer.getDeleted()) {
            throw new CustomerNotFoundException(customerId);
        }
        return customerMapper.toDTO(customer);
    }


    @Override
    public CustomerResponseDTO update(Long customerId, CustomerUpdateDTO dto) {
        if (!customerRepository.existsById(customerId)) throw new CustomerNotFoundException(customerId);

        Customer customer = getEntityById(customerId);
        customerMapper.updateCustomerFromDTO(dto, customer);

        return customerMapper.toDTO(customerRepository.save(customer));
    }

    @Override
    public void delete(Long customerId) {
        Customer customer = getEntityById(customerId);
        customer.setDeleted(true);

        customerMapper.toDTO(customerRepository.save(customer));
    }

    @Override
    public Customer getEntityById(Long customerId) {
        return Optional.ofNullable(customerId)
                .map(customer -> customerRepository.findByIdAndBusiness_Id(customerId, businessContext.getCurrentBusinessId())
                        .orElseThrow(() -> new CustomerNotFoundException(customerId)))
                .orElse(null);
    }
}