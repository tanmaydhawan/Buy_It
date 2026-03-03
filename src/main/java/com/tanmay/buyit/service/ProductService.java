package com.tanmay.buyit.service;

import com.tanmay.buyit.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    Product createProduct(Product product, Long categoryId);
    List<Product> getAllProducts();

}
