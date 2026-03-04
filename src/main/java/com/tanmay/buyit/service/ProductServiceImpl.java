package com.tanmay.buyit.service;

import com.tanmay.buyit.dto.CategoryResponse;
import com.tanmay.buyit.dto.ProductRequest;
import com.tanmay.buyit.dto.ProductResponse;
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
    public ProductResponse createProduct(ProductRequest productRequest) {

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                                    .orElseThrow(CategoryNotFoundException::new);

        Product product = mapToEntity(productRequest);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return mapToDto(savedProduct);
    }

    private ProductResponse mapToDto(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(
                        CategoryResponse.builder()
                                .id(product.getCategory().getId())
                                .name(product.getCategory().getName())
                                .build()
                )
                .build();
    }

    private Product mapToEntity(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .build();
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }
}
