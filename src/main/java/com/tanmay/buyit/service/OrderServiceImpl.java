package com.tanmay.buyit.service;

import com.tanmay.buyit.dto.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

    @Override
    public OrderResponse placeOrderForUser() {

//  1. Get logged-in user

//  2. Fetch user's cart
//  3. Validate cart is NOT empty
//  4. Validate products still exist
//  5. Validate stock availability
//  6. Calculate final price
//  7. Create order
//  8. Create order items
//  9. Reduce product stock
//  10. Clear cart
//  11. Return order response
        return null;
    }
}
