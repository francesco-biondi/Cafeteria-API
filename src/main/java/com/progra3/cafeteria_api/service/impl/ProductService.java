package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.product.ProductNotFoundException;
import com.progra3.cafeteria_api.model.dto.ProductComponentRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductResponseDTO;
import com.progra3.cafeteria_api.model.mapper.ProductMapper;
import com.progra3.cafeteria_api.model.entity.Category;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductComponent;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import com.progra3.cafeteria_api.repository.ProductRepository;
import com.progra3.cafeteria_api.service.IProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.progra3.cafeteria_api.model.enums.CompositionType.*;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;
    private final ProductGroupService productGroupService;
    private final ProductComponentService productComponentService;

    @Transactional
    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Category category = categoryService.getEntityById(productRequestDTO.categoryId());
        Product product = productMapper.toEntity(productRequestDTO, category);
        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findByIdWithComponents(id)
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

    @Transactional
    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        Category category = categoryService.getEntityById(productRequestDTO.categoryId());
        Product updatedProduct = productMapper.toEntity(productRequestDTO, category);
        updatedProduct.setId(id);

        return productMapper.toDTO(productRepository.save(updatedProduct));
    }

    @Transactional
    @Override
    public ProductResponseDTO updateProduct(Product updatedProduct) {
        return productMapper.toDTO(productRepository.save(updatedProduct));
    }

    @Transactional
    @Override
    public ProductResponseDTO deleteProduct(Long id) {
        Product product = getEntityById(id);
        product.setDeleted(true);

        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public Product getEntityById(Long productId) {
        return productRepository.findByIdWithComponents(productId).orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Transactional
    @Override
    public ProductResponseDTO assignGroupToProduct(Long productId, Long groupId) {
        Product product = getEntityById(productId);
        ProductGroup group = productGroupService.getEntityById(groupId);

        product.getProductGroups().add(group);
        adjustComposite(product);

        return productMapper.toDTO(productRepository.save(product));
    }

    @Transactional
    @Override
    public ProductResponseDTO removeGroupFromProduct(Long productId, Long groupId) {
        Product product = getEntityById(productId);
        ProductGroup group = productGroupService.getEntityById(groupId);

        product.getProductGroups().remove(group);
        adjustComposite(product);

        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO addComponentsToProduct(Long productId, List<ProductComponentRequestDTO> dtos) {
        Product product = getEntityById(productId);
        dtos.forEach(dto -> addComponent(product, dto));
        adjustComposite(product);
        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO updateProductComponent(Long productId, Long componentId, Integer quantity) {
        Product product = getEntityById(productId);
        product.getComponents().stream()
                .filter(component -> component.getId().equals(componentId))
                .findFirst()
                .ifPresent(component -> component.setQuantity(quantity));

        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO removeComponentFromProduct(Long productId, Long componentId) {
        Product product = getEntityById(productId);
        product.getComponents().removeIf(component -> component.getId().equals(componentId));
        adjustComposite(product);

        return productMapper.toDTO(productRepository.save(product));
    }

    private void addComponent(Product parentProduct, ProductComponentRequestDTO dto) {
        ProductComponent component = productComponentService.createProductComponent(dto);
        component.setParentProduct(parentProduct);
        parentProduct.getComponents().add(component);

    }

    private void adjustComposite(Product product) {
        boolean hasGroups = !product.getProductGroups().isEmpty();
        boolean hasComponents = !product.getComponents().isEmpty();

        if (hasGroups && hasComponents) {
            product.setCompositionType(FIXED_SELECTABLE);
        } else if (hasGroups) {
            product.setCompositionType(SELECTABLE);
        } else if (hasComponents) {
            product.setCompositionType(FIXED);
        } else {
            product.setCompositionType(NONE);
        }

        product.setComposite(hasGroups || hasComponents);
    }


}
