package com.progra3.cafeteria_api.security;

import org.springframework.stereotype.Component;

@Component
public class BusinessContext {
    public Long getCurrentBusinessId() {
//        var auth = SecurityContextHolder.getContext().getAuthentication();
//        return ((CustomUserDetails) auth.getPrincipal()).getBusinessId();
        return 2L;
    }
}