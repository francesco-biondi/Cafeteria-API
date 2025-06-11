package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.business.BusinessNotFoundException;
import com.progra3.cafeteria_api.model.dto.BusinessRequestDTO;
import com.progra3.cafeteria_api.model.dto.BusinessResponseDTO;
import com.progra3.cafeteria_api.model.mapper.BusinessMapper;
import com.progra3.cafeteria_api.model.entity.Business;
import com.progra3.cafeteria_api.repository.BusinessRepository;
import com.progra3.cafeteria_api.service.port.IBusinessService;
import com.progra3.cafeteria_api.security.BusinessContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessService implements IBusinessService {
    private final BusinessContext businessContext;

    private final BusinessRepository businessRepository;

    private final BusinessMapper businessMapper;

    @Override
    public BusinessResponseDTO createBusiness(BusinessRequestDTO dto) {
        return businessMapper.toDTO(businessRepository.save(businessMapper.toEntity(dto)));
    }

    @Override
    public Long getCurrentBusinessId() {
        return businessContext.getCurrentBusinessId();
    }

    @Override
    public Business getCurrentBusiness() {
        Long businessId = businessContext.getCurrentBusinessId();
        return businessRepository.findById(businessId)
                .orElseThrow(() -> new BusinessNotFoundException(businessId));
    }
}
