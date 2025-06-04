package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.ProductOptionRequestDTO;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import com.progra3.cafeteria_api.model.entity.ProductOption;

public interface IProductOptionService {
    ProductOption createProductOption(ProductGroup productGroup, ProductOptionRequestDTO dto);

    ProductOption getEntityById(Long id);

    ProductOption updateProductOption(ProductOption productOption, ProductOptionRequestDTO dto);
}
