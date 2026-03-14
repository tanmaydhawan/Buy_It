package com.tanmay.buyit.controller;

import com.tanmay.buyit.dto.ProductRequest;
import com.tanmay.buyit.dto.ProductResponse;
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

    @GetMapping
    @PreAuthorize("hasAuthority('BUYIT_ADMIN')")
    public ResponseEntity<List<ProductResponse>> fetchAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('BUYIT_ADMIN')")
    public ResponseEntity<ProductResponse> createProducts(@RequestBody ProductRequest productRequest){
        return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('BUYIT_ADMIN')")
    public ResponseEntity<ProductResponse> editExistingProduct (@PathVariable Long id, @RequestBody ProductRequest productRequest){
        return new ResponseEntity<>(productService.editProduct(id, productRequest), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('BUYIT_ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('BUYIT_ADMIN')")
    public ResponseEntity<List<ProductResponse>> searchProductsByName (@RequestParam String name){
        return new ResponseEntity<>(productService.findProductsByName(name), HttpStatus.OK);
    }
}
