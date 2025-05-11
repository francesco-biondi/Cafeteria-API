package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.CannotDeleteCategoryException;
import com.progra3.cafeteria_api.exception.CategoryNotFoundException;
import com.progra3.cafeteria_api.model.dto.CategoryRequestDTO;
import com.progra3.cafeteria_api.model.dto.CategoryResponseDTO;
import com.progra3.cafeteria_api.model.dto.mapper.CategoryMapper;
import com.progra3.cafeteria_api.model.entity.Category;
import com.progra3.cafeteria_api.repository.ICategoryRepository;
import com.progra3.cafeteria_api.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryMapper.toEntity(categoryRequestDTO);
        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public Category getEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDTO)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));

        Category updatedCategory = categoryMapper.toEntity(categoryRequestDTO);
        updatedCategory.setName(categoryRequestDTO.name());
        return categoryMapper.toDTO(categoryRepository.save(updatedCategory));
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
        if (!category.getProducts().isEmpty()) {
            throw new CannotDeleteCategoryException("Cannot delete category with associated products.");
        }
        categoryRepository.delete(category);
    }
}
