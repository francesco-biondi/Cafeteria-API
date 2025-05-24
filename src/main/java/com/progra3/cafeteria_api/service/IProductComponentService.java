package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.ProductComponentRequestDTO;
import com.progra3.cafeteria_api.model.entity.ProductComponent;

public interface IProductComponentService {
    ProductComponent createProductComponent(ProductComponentRequestDTO dto);
}
