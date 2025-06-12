package com.progra3.cafeteria_api.service.helper;

import com.progra3.cafeteria_api.exception.product.ProductNotFoundException;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.repository.ProductRepository;
import com.progra3.cafeteria_api.service.impl.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFinderService {
    private final ProductRepository productRepository;
    private final BusinessService businessService;

    public Product getEntityById(Long productId) {
        return productRepository.findByIdAndBusiness_IdWithComponents(productId, businessService.getCurrentBusinessId())
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }
}