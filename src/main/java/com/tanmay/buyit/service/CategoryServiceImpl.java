package com.tanmay.buyit.service;

import com.tanmay.buyit.dto.CategoryRequest;
import com.tanmay.buyit.dto.CategoryResponse;
import com.tanmay.buyit.entity.Category;
import com.tanmay.buyit.exception.CategoryNotFoundException;
import com.tanmay.buyit.repo.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {

        Category category = mapToEntity(categoryRequest);

        Category savedCategory = categoryRepository.save(category);

        return mapToDto(savedCategory);

    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        categoryRepository.delete(category);
    }

    private CategoryResponse mapToDto(Category savedCategory) {
        return CategoryResponse.builder()
                .id(savedCategory.getId())
                .name(savedCategory.getName())
                .build();
    }

    private Category mapToEntity(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getName())
                .build();
    }
}
