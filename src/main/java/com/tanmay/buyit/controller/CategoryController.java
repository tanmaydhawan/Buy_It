package com.tanmay.buyit.controller;

import com.tanmay.buyit.dto.CategoryRequest;
import com.tanmay.buyit.dto.CategoryResponse;
import com.tanmay.buyit.service.CategoryService;
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
@RequestMapping("/category")
@AllArgsConstructor
@Tag(name = "Category APIs", description = "Operations related to Categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAuthority('BUYIT_ADMIN')")
    @Operation(summary = "Loading all Categories")
    public ResponseEntity<List<CategoryResponse>> fetchAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('BUYIT_ADMIN')")
    @Operation(summary = "Creating a new Category")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        return new ResponseEntity<>(categoryService.createCategory(categoryRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('BUYIT_ADMIN')")
    @Operation(summary = "Delete an existing Category")
    public ResponseEntity<?> deleteCategory (@PathVariable Long id){
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Category Deleted Successfully");
    }
}
