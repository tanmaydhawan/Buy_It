package com.tanmay.buyit.service;

import com.tanmay.buyit.dto.CartRequest;
import com.tanmay.buyit.dto.CartResponse;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    CartResponse addToCart (CartRequest cartRequest);
    CartResponse findUserCart();
    CartResponse deleteCartItem(Long productId);
}