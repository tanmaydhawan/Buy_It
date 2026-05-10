package com.tanmay.buyit.controller;

import com.tanmay.buyit.dto.ProductRequest;
import com.tanmay.buyit.dto.ProductResponse;
import com.tanmay.buyit.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
@Tag(name = "Product APIs", description = "Operations related to products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all Products")
    public ResponseEntity<List<ProductResponse>> fetchAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a Product")
    @PreAuthorize("hasAuthority('BUYIT_ADMIN')")
    public ResponseEntity<ProductResponse> createProducts(@Valid @RequestBody ProductRequest productRequest){
        return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit existing Product")
    @PreAuthorize("hasAuthority('BUYIT_ADMIN')")
    public ResponseEntity<ProductResponse> editExistingProduct (@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest){
        return new ResponseEntity<>(productService.editProduct(id, productRequest), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Product")
    @PreAuthorize("hasAuthority('BUYIT_ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by Name")
    @PreAuthorize("hasAuthority('BUYIT_ADMIN')")
    public ResponseEntity<List<ProductResponse>> searchProductsByName (@RequestParam String name){
        return new ResponseEntity<>(productService.findProductsByName(name), HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    @Operation(summary = "Get Products by Category")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory (@PathVariable Long id){
        return new ResponseEntity<>(productService.getProductByCategoryId(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Product by Id")
    public ResponseEntity<ProductResponse> getProductsById(@PathVariable Long id){
        return new ResponseEntity<>(productService.findProductByProductId(id), HttpStatus.OK);
    }
}
