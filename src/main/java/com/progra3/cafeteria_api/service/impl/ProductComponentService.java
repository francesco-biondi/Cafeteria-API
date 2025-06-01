package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.model.dto.ProductComponentRequestDTO;
import com.progra3.cafeteria_api.model.mapper.ProductComponentMapper;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductComponent;
import com.progra3.cafeteria_api.service.IProductComponentService;
import com.progra3.cafeteria_api.service.helper.ProductFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductComponentService implements IProductComponentService {
    private final ProductComponentMapper productComponentMapper;
    private final ProductFinderService productFinderService;

    @Override
    public ProductComponent createProductComponent(ProductComponentRequestDTO dto) {
        Product product = productFinderService.getEntityById(dto.productId());

        return productComponentMapper.toEntity(dto, product);
    }
}
