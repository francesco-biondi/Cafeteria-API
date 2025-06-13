package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.business.BusinessNotFoundException;
import com.progra3.cafeteria_api.model.dto.BusinessRequestDTO;
import com.progra3.cafeteria_api.model.dto.BusinessResponseDTO;
import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.model.mapper.BusinessMapper;
import com.progra3.cafeteria_api.model.entity.Business;
import com.progra3.cafeteria_api.repository.BusinessRepository;
import com.progra3.cafeteria_api.service.port.IBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessService implements IBusinessService {

    private final BusinessRepository businessRepository;

    private final BusinessMapper businessMapper;

    @Override
    public BusinessResponseDTO createBusiness(BusinessRequestDTO dto) {
        Business business = businessMapper.toEntity(dto);

        Employee owner = business.getOwner();
        owner.setDeleted(false);
        owner.setBusiness(business);

        business.setOwner(owner);
        business.getEmployees().add(owner);


        return businessMapper.toDTO(businessRepository.save(business));
    }

    @Override
    public Business getEntityById(Long id) {
        return businessRepository.findById(id)
                .orElseThrow(() -> new BusinessNotFoundException(id));
    }

}
