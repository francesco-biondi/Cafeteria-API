package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.CannotDeleteProductGroupException;
import com.progra3.cafeteria_api.exception.ProductGroupNotFoundException;
import com.progra3.cafeteria_api.model.dto.ProductGroupOptionRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductGroupOptionResponseDTO;
import com.progra3.cafeteria_api.model.dto.ProductGroupRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductGroupResponseDTO;
import com.progra3.cafeteria_api.model.dto.mapper.ProductGroupMapper;
import com.progra3.cafeteria_api.model.dto.mapper.ProductGroupOptionMapper;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import com.progra3.cafeteria_api.model.entity.ProductGroupOption;
import com.progra3.cafeteria_api.repository.ProductGroupRepository;
import com.progra3.cafeteria_api.service.IProductGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductGroupService implements IProductGroupService {
    private final ProductGroupMapper productGroupMapper;
    private final ProductGroupOptionMapper productGroupOptionMapper;
    private final ProductGroupRepository productGroupRepository;
    private final ProductGroupOptionService productGroupOptionService;

    @Override
    public ProductGroupResponseDTO createProductGroup(ProductGroupRequestDTO dto) {
        ProductGroup productGroup = productGroupMapper.toEntity(dto);

        return productGroupMapper.toDTO(productGroupRepository.save(productGroup));
    }

    @Override
    public ProductGroup getEntityById(Long productGroupId) {
        return productGroupRepository.findById(productGroupId)
                .orElseThrow(() -> new ProductGroupNotFoundException(productGroupId));
    }

    @Override
    public ProductGroupResponseDTO getProductGroupById(Long productGroupId) {
        ProductGroup productGroup = getEntityById(productGroupId);

        return productGroupMapper.toDTO(productGroup);
    }

    @Override
    public List<ProductGroupResponseDTO> getAllProductGroups() {
        List<ProductGroup> productGroups = productGroupRepository.findAll();

        return productGroupMapper.toDTOList(productGroups);
    }

    @Override
    public ProductGroupResponseDTO updateProductGroup(Long productGroupId, ProductGroupRequestDTO dto) {
        ProductGroup productGroup = getEntityById(productGroupId);

        productGroup.setName(dto.name());
        productGroup.setMinQuantity(dto.minQuantity());
        productGroup.setMaxQuantity(dto.maxQuantity());

        return productGroupMapper.toDTO(productGroupRepository.save(productGroup));
    }

    @Override
    public void deleteProductGroup(Long productGroupId) {
        ProductGroup productGroup = getEntityById(productGroupId);

        if (!productGroup.getUsedByProducts().isEmpty()) {
            throw new CannotDeleteProductGroupException(productGroupId);
        }

        productGroupRepository.delete(productGroup);
    }

    @Override
    public List<ProductGroupOptionResponseDTO> addOptions(Long id, List<ProductGroupOptionRequestDTO> dtos) {
        ProductGroup productGroup = getEntityById(id);

        return dtos.stream()
                .map(dto -> addOption(productGroup, dto))
                .toList();
    }

    @Override
    public List<ProductGroupOptionResponseDTO> getOptions(Long id) {
        return getEntityById(id).getOptions()
                .stream()
                .map(productGroupOptionMapper::toDTO)
                .toList();
    }


    @Override
    public ProductGroupOptionResponseDTO updateOption(Long groupId, Long optionId, ProductGroupOptionRequestDTO dto) {
        getEntityById(groupId);

        ProductGroupOption updatedOption = productGroupOptionService.updateProductGroupOption(optionId, dto);

        return productGroupOptionMapper.toDTO(updatedOption);
    }

    @Override
    public ProductGroupOptionResponseDTO removeOption(Long id, Long optionId) {
        ProductGroup productGroup = getEntityById(id);
        ProductGroupOption option = productGroupOptionService.getEntityById(optionId);
        productGroup.getOptions().remove(option);

        productGroupRepository.save(productGroup);

        return productGroupOptionMapper.toDTO(option);
    }

    private ProductGroupOptionResponseDTO addOption(ProductGroup productGroup, ProductGroupOptionRequestDTO dto) {
        ProductGroupOption option = productGroupOptionService.createProductGroupOption(productGroup, dto);

        productGroup.getOptions().add(option);

        return productGroupOptionMapper.toDTO(option);
    }
}
