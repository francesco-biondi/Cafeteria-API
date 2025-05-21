package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.dto.ProductGroupResponseDTO;
import com.progra3.cafeteria_api.model.dto.ProductRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductResponseDTO;
import com.progra3.cafeteria_api.model.entity.Product;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IProductService {
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    ProductResponseDTO getProductById(Long id);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO updateProduct(ProductRequestDTO productRequestDTO);
    ProductResponseDTO deleteProduct(Long id);

    Product getEntityById (Long productId);

    @Transactional
    ProductResponseDTO assignGroupToProduct(Long productId, Long groupId);

    @Transactional
    ProductResponseDTO removeGroupFromProduct(Long productId, Long groupId);
}
