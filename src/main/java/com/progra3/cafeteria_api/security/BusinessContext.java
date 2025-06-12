package com.progra3.cafeteria_api.security;

import com.progra3.cafeteria_api.exception.business.BusinessNotFoundException;
import com.progra3.cafeteria_api.model.entity.Business;
import com.progra3.cafeteria_api.repository.BusinessRepository;
import com.progra3.cafeteria_api.service.impl.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusinessContext {

    private final BusinessService businessService;

    public Long getCurrentBusinessId() {
//        var auth = SecurityContextHolder.getContext().getAuthentication();
//        return ((CustomUserDetails) auth.getPrincipal()).getBusinessId();
        return 1L;
    }

    public Business getCurrentBusiness() {
        Long businessId = getCurrentBusinessId();
        return businessService.getEntityById(businessId);
    }
}