package com.tanmay.buyit.controller;

import com.tanmay.buyit.dto.CategoryRequest;
import com.tanmay.buyit.dto.CategoryResponse;
import com.tanmay.buyit.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAuthority('BUYIT_ADMIN')")
    public ResponseEntity<List<CategoryResponse>> fetchAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('BUYIT_ADMIN')")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        return new ResponseEntity<>(categoryService.createCategory(categoryRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('BUYIT_ADMIN')")
    public ResponseEntity<?> deleteCategory (@PathVariable Long id){
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Category Deleted Successfully");
    }
}
