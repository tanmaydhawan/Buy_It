package com.tanmay.buyit.controller;

import com.tanmay.buyit.dto.CartRequest;
import com.tanmay.buyit.dto.CartResponse;
import com.tanmay.buyit.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addToCart(@RequestBody CartRequest cartRequest){
        return ResponseEntity.ok(cartService.addToCart(cartRequest));
    }
}
