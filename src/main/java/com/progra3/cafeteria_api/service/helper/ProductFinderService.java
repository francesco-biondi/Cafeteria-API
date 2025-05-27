package com.progra3.cafeteria_api.service.helper;

import com.progra3.cafeteria_api.exception.product.ProductNotFoundException;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFinderService {
    private final ProductRepository productRepository;

    public Product getEntityById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }
}