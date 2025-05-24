package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.ProductNotFoundException;
import com.progra3.cafeteria_api.model.dto.ProductRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductResponseDTO;
import com.progra3.cafeteria_api.model.dto.mapper.ProductGroupMapper;
import com.progra3.cafeteria_api.model.dto.mapper.ProductMapper;
import com.progra3.cafeteria_api.model.entity.Category;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import com.progra3.cafeteria_api.repository.ProductRepository;
import com.progra3.cafeteria_api.service.IProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;
    private final ProductGroupService productGroupService;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Category category = categoryService.getEntityById(productRequestDTO.categoryId());
        Product product = productMapper.toEntity(productRequestDTO, category);
        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO updateProduct(ProductRequestDTO productRequestDTO) {
        Category category = categoryService.getEntityById(productRequestDTO.categoryId());
        Product updatedProduct = productMapper.toEntity(productRequestDTO, category);

        return productMapper.toDTO(productRepository.save(updatedProduct));
    }

    @Override
    public ProductResponseDTO deleteProduct(Long id) {
        Product product = getEntityById(id);
        product.setDeleted(true);

        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public Product getEntityById (Long productId){
        return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Transactional
    @Override
    public ProductResponseDTO assignGroupToProduct (Long productId, Long groupId) {
        Product product = getEntityById(productId);
        ProductGroup group = productGroupService.getEntityById(groupId);

        product.getProductGroups().add(group);

        return productMapper.toDTO(productRepository.save(product));
    }

    @Transactional
    @Override
    public ProductResponseDTO removeGroupFromProduct(Long productId, Long groupId) {
        Product product = getEntityById(productId);
        ProductGroup group = productGroupService.getEntityById(groupId);

        product.getProductGroups().remove(group);

        return productMapper.toDTO(productRepository.save(product));
    }

    private void adjustComposite (Product product) {
        product.setComposite(!product.getProductGroups().isEmpty());
    }
}
