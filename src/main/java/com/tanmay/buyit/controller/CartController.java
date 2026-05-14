package com.tanmay.buyit.controller;

import com.tanmay.buyit.dto.CartRequest;
import com.tanmay.buyit.dto.CartResponse;
import com.tanmay.buyit.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
@Tag(name = "Cart APIs", description = "Operations related to Cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    @Operation(summary = "Add to Cart")
    public ResponseEntity<CartResponse> addToCart(@Valid @RequestBody CartRequest cartRequest){
        return ResponseEntity.ok(cartService.addToCart(cartRequest));
    }

    @GetMapping("/items")
    @Operation(summary = "Get user's Cart")
    public ResponseEntity<CartResponse> getUserCart (){
        return ResponseEntity.ok(cartService.findUserCart());
    }

    @DeleteMapping("/items/{productId}")
    @Operation(summary = "Delete product from Cart")
    public ResponseEntity<CartResponse> deleteProductFromCart (@PathVariable Long productId){
        return ResponseEntity.ok(cartService.deleteCartItem(productId));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete user's entire cart")
    public ResponseEntity<?> deleteCartForUser(){
        cartService.deleteUserCart();
        return ResponseEntity.noContent().build();
    }
}
