package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.ProductGroupOptionRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductGroupOptionResponseDTO;
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

    List<ProductGroupOptionResponseDTO> addOptions(Long id, List<ProductGroupOptionRequestDTO> dtos);

    List<ProductGroupOptionResponseDTO> getOptions(Long id);

    ProductGroupOptionResponseDTO updateOption(Long id, Long optionId, ProductGroupOptionRequestDTO dto);

    ProductGroupOptionResponseDTO removeOption(Long id, Long optionId);

}
