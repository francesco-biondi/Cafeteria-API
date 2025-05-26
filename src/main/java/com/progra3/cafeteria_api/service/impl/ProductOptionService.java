package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.ProductOptionNotFoundException;
import com.progra3.cafeteria_api.model.dto.ProductOptionRequestDTO;
import com.progra3.cafeteria_api.model.dto.mapper.ProductOptionMapper;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import com.progra3.cafeteria_api.model.entity.ProductOption;
import com.progra3.cafeteria_api.repository.ProductOptionRepository;
import com.progra3.cafeteria_api.service.IProductOptionService;
import com.progra3.cafeteria_api.service.helper.ProductFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductOptionService implements IProductOptionService {
    private final ProductOptionRepository productOptionRepository;
    private final ProductOptionMapper productOptionMapper;
    private final ProductFinderService productFinderService;

    @Override
    public ProductOption createProductOption(ProductGroup productGroup, ProductOptionRequestDTO dto) {
        Product product = productFinderService.getEntityById(dto.productId());

        return productOptionRepository.save(productOptionMapper.toEntity(dto, productGroup, product));
    }

    @Override
    public ProductOption getEntityById(Long productOptionId) {
        return productOptionRepository.findById(productOptionId)
                .orElseThrow(() -> new ProductOptionNotFoundException(productOptionId));
    }

    @Override
    public ProductOption updateProductOption(Long productOptionId, ProductOptionRequestDTO dto) {
        ProductOption productOption = getEntityById(productOptionId);

        productOption.setMaxQuantity(dto.maxQuantity());
        productOption.setPriceIncrease(dto.priceIncrease());

        return productOptionRepository.save(productOption);
    }
}
