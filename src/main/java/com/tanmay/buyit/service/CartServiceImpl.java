package com.tanmay.buyit.service;

import com.tanmay.buyit.dto.CartRequest;
import com.tanmay.buyit.dto.CartResponse;
import com.tanmay.buyit.entity.Cart;
import com.tanmay.buyit.entity.CartItem;
import com.tanmay.buyit.entity.Product;
import com.tanmay.buyit.entity.User;
import com.tanmay.buyit.exception.ProductNotFoundException;
import com.tanmay.buyit.exception.UserNotFoundException;
import com.tanmay.buyit.repo.CartRepository;
import com.tanmay.buyit.repo.ProductRepository;
import com.tanmay.buyit.repo.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

public class CartServiceImpl implements CartService{

    private UserRepository userRepository;
    private CartRepository cartRepository;
    private ProductRepository productRepository;

    @Override
    public CartResponse addToCart(CartRequest cartRequest) {

        String userName = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new UserNotFoundException(userName));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(createNewCart(user)));

        Product product = productRepository.findById(cartRequest.getProductId())
                .orElseThrow(ProductNotFoundException::new);

        Optional<CartItem> cartItem = cart.getCartItemList().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if(cartItem.isPresent()){
            CartItem cartItem1 = cartItem.get();
            cartItem1.setQuantity(cartItem1.getQuantity() + cartRequest.getQuantity());
        }else{
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(cartRequest.getQuantity());
            newItem.setCart(cart);
        }

        return null;
    }

    private Cart createNewCart(User user) {
        return Cart.builder()
                .user(user)
                .cartItemList(new ArrayList<>())
                .totalPrice(BigDecimal.ZERO)
                .build();
    }
}
