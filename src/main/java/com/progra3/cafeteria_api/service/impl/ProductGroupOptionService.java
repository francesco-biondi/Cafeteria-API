package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.ProductGroupOptionNotFoundException;
import com.progra3.cafeteria_api.model.dto.ProductGroupOptionRequestDTO;
import com.progra3.cafeteria_api.model.dto.mapper.ProductGroupOptionMapper;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import com.progra3.cafeteria_api.model.entity.ProductGroupOption;
import com.progra3.cafeteria_api.repository.ProductGroupOptionRepository;
import com.progra3.cafeteria_api.service.IProductGroupOptionService;
import com.progra3.cafeteria_api.service.helper.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductGroupOptionService implements IProductGroupOptionService {
    private final ProductGroupOptionRepository productGroupOptionRepository;
    private final ProductGroupOptionMapper productGroupOptionMapper;
    private final ProductQueryService productQueryService;

    @Override
    public ProductGroupOption createProductGroupOption(ProductGroup productGroup, ProductGroupOptionRequestDTO dto) {
        Product product = productQueryService.getEntityById(dto.productId());

        return productGroupOptionRepository.save(productGroupOptionMapper.toEntity(dto, productGroup, product));
    }

    @Override
    public ProductGroupOption getEntityById(Long productGroupOptionId) {
        return productGroupOptionRepository.findById(productGroupOptionId)
                .orElseThrow(() -> new ProductGroupOptionNotFoundException(productGroupOptionId));
    }

    @Override
    public ProductGroupOption updateProductGroupOption(Long productGroupOptionId, ProductGroupOptionRequestDTO dto) {
        ProductGroupOption productGroupOption = getEntityById(productGroupOptionId);

        productGroupOption.setMaxQuantity(dto.maxQuantity());
        productGroupOption.setPriceIncrease(dto.priceIncrease());

        return productGroupOptionRepository.save(productGroupOption);
    }
}
