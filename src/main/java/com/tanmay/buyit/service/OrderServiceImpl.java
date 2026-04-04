package com.tanmay.buyit.service;

import com.tanmay.buyit.dto.OrderResponse;
import com.tanmay.buyit.entity.Cart;
import com.tanmay.buyit.entity.CartItem;
import com.tanmay.buyit.entity.User;
import com.tanmay.buyit.exception.CartNotFoundForUserException;
import com.tanmay.buyit.exception.ProductNotFoundException;
import com.tanmay.buyit.exception.UserNotFoundException;
import com.tanmay.buyit.repo.CartRepository;
import com.tanmay.buyit.repo.ProductRepository;
import com.tanmay.buyit.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    @Override
    public OrderResponse placeOrderForUser() {

//  1. Get logged-in user
        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(userId).orElseThrow(() -> new UserNotFoundException(userId));

//  2. Fetch user's cart
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new CartNotFoundForUserException(userId));

//  3. Validate cart is NOT empty (Add specific exception)
        if(cart.getCartItemList() != null && !cart.getCartItemList().isEmpty()){
            throw new IllegalStateException("Cart is Empty, cannot place order!");
        }

//  4. Validate products still exist
        for(CartItem ci : cart.getCartItemList()){
            Long productId = ci.getProduct().getId();
            boolean exists = productRepository.existsById(productId);

            if(!exists){
                throw new ProductNotFoundException();
            }
        }

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
