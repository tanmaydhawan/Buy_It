package com.tanmay.buyit.service;

import com.tanmay.buyit.dto.ProductRequest;
import com.tanmay.buyit.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProducts();
    void deleteById(Long id);
    ProductResponse editProduct(Long id, ProductRequest productRequest);

    List<ProductResponse> findProductsByName(String name);
}
