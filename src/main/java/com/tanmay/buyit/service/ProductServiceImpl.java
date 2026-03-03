package com.tanmay.buyit.service;

import com.tanmay.buyit.entity.Category;
import com.tanmay.buyit.entity.Product;
import com.tanmay.buyit.exception.CategoryNotFoundException;
import com.tanmay.buyit.repo.CategoryRepository;
import com.tanmay.buyit.repo.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(Product product, Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                                    .orElseThrow(CategoryNotFoundException::new);

        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
