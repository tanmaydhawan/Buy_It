package com.tanmay.buyit.service;

import com.tanmay.buyit.dto.CategoryRequest;
import com.tanmay.buyit.dto.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> getAllCategories();

}
