package com.tanmay.buyit.controller;

import com.tanmay.buyit.entity.Product;
import com.tanmay.buyit.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('BUYIT_ADMIN')")
    public ResponseEntity<List<Product>> fetchAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('BUYIT_ADMIN')")
    public ResponseEntity<Product> createProducts(@RequestBody Product product, @RequestParam Long categoryId){
        return new ResponseEntity<>(productService.createProduct(product, categoryId), HttpStatus.OK);
    }
}
