package com.tanmay.buyit.service;

import com.tanmay.buyit.dto.CategoryResponse;
import com.tanmay.buyit.dto.ProductRequest;
import com.tanmay.buyit.dto.ProductResponse;
import com.tanmay.buyit.entity.Category;
import com.tanmay.buyit.entity.Product;
import com.tanmay.buyit.exception.CategoryNotFoundException;
import com.tanmay.buyit.exception.ProductNotFoundException;
import com.tanmay.buyit.repo.CategoryRepository;
import com.tanmay.buyit.repo.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @CacheEvict(value = {"products", "products_by_category"}, allEntries = true)
    public ProductResponse createProduct(ProductRequest productRequest) {

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                                    .orElseThrow(CategoryNotFoundException::new);

        Product product = mapToEntity(productRequest);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return mapToDto(savedProduct);
    }

    @Override
    @Cacheable(value = "products", key = "'all'")
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    @CacheEvict(value = {"products", "products_by_category"}, allEntries = true)
    public void deleteById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        productRepository.delete(product);
    }

    @Override
    @CacheEvict(value = {"products", "products_by_category"}, allEntries = true)
    public ProductResponse editProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(CategoryNotFoundException::new);

        product.setName(productRequest.getName());
        product.setStock(productRequest.getStock());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);

        return mapToDto(updatedProduct);
    }

    @Override
    @Cacheable(value = "products_search", key = "#name")
    public List<ProductResponse> findProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    @Cacheable(value = "products_by_category", key = "#categoryId")
    public List<ProductResponse> getProductByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToDto)
                .toList();
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
}
