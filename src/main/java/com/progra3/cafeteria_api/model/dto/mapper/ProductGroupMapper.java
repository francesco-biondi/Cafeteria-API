package com.progra3.cafeteria_api.model.dto.mapper;

import com.progra3.cafeteria_api.model.dto.ProductGroupRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductGroupResponseDTO;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductGroupOptionMapper.class})
public interface ProductGroupMapper {

     ProductGroupResponseDTO toDTO(ProductGroup productGroup);

     List<ProductGroupResponseDTO> toDTOList(List<ProductGroup> productGroups);

     @Mapping(target = "options", expression = "java(java.util.List.of())")
     ProductGroup toEntity(ProductGroupRequestDTO productGroupRequestDTO);
}
