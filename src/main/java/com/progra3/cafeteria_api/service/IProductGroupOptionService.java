package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.ProductGroupOptionRequestDTO;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import com.progra3.cafeteria_api.model.entity.ProductGroupOption;

import java.util.List;

public interface IProductGroupOptionService {
    ProductGroupOption createProductGroupOption (ProductGroup productGroup, ProductGroupOptionRequestDTO dto);

    ProductGroupOption getEntityById(Long productGroupOptionId);

    ProductGroupOption updateProductGroupOption(Long productGroupOptionId, ProductGroupOptionRequestDTO dto);
}
