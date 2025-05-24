package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.ProductOptionRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductOptionResponseDTO;
import com.progra3.cafeteria_api.model.dto.ProductGroupRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductGroupResponseDTO;
import com.progra3.cafeteria_api.model.entity.ProductGroup;

import java.util.List;

public interface IProductGroupService {
    ProductGroupResponseDTO createProductGroup(ProductGroupRequestDTO dto);

    ProductGroup getEntityById(Long productGroupId);

    ProductGroupResponseDTO getProductGroupById(Long productGroupId);

    List<ProductGroupResponseDTO> getAllProductGroups();

    ProductGroupResponseDTO updateProductGroup(Long productGroupId, ProductGroupRequestDTO dto);

    void deleteProductGroup(Long productGroupId);

    List<ProductOptionResponseDTO> addProductOptions(Long id, List<ProductOptionRequestDTO> dtos);

    List<ProductOptionResponseDTO> getProductOptions(Long id);

    ProductOptionResponseDTO updateProductOption(Long id, Long optionId, ProductOptionRequestDTO dto);

    ProductOptionResponseDTO removeProductOption(Long id, Long optionId);

}
