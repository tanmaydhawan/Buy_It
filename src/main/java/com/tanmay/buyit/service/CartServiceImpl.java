package com.tanmay.buyit.service;

import com.tanmay.buyit.dto.CartItemResponse;
import com.tanmay.buyit.dto.CartRequest;
import com.tanmay.buyit.dto.CartResponse;
import com.tanmay.buyit.entity.Cart;
import com.tanmay.buyit.entity.CartItem;
import com.tanmay.buyit.entity.Product;
import com.tanmay.buyit.entity.User;
import com.tanmay.buyit.exception.CartItemNotFoundException;
import com.tanmay.buyit.exception.CartNotFoundForUserException;
import com.tanmay.buyit.exception.ProductNotFoundException;
import com.tanmay.buyit.exception.UserNotFoundException;
import com.tanmay.buyit.repo.CartRepository;
import com.tanmay.buyit.repo.ProductRepository;
import com.tanmay.buyit.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public CartResponse addToCart(CartRequest cartRequest) {

        //Getting the logged-in user
        String userName = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new UserNotFoundException(userName));

        //Find existing cart or create a new one
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(createNewCart(user)));

        //Check if the product exists =
        Product product = productRepository.findById(cartRequest.getProductId())
                .orElseThrow(ProductNotFoundException::new);

        //Checking if cartItem with  the product exists
        Optional<CartItem> cartItem = cart.getCartItemList().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        //If exists then we add the quantity else creating a new cart item
        if (cartItem.isPresent()){
            CartItem cartItem1 = cartItem.get();
            cartItem1.setQuantity(cartItem1.getQuantity() + cartRequest.getQuantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(cartRequest.getQuantity());
            newItem.setCart(cart);
            cart.getCartItemList().add(newItem);
        }

        //Total Amount of the cart items
        BigDecimal totalAmount = cart.getCartItemList().stream()
                .map(ci -> ci.getProduct().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalAmount);

        Cart savedCart = cartRepository.save(cart);

        List<CartItemResponse> cartItemResponseList = mapCartItemsToDto(savedCart);

        return mapToCartResponse(cartItemResponseList, savedCart);
    }

    @Override
    public CartResponse findUserCart() {

        String userName = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(userName).orElseThrow(()-> new UserNotFoundException(userName));

        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new CartNotFoundForUserException(userName));

        List<CartItemResponse> cartItemResponseList = mapCartItemsToDto(cart);

        return mapToCartResponse(cartItemResponseList, cart);
    }

    @Override
    public CartResponse deleteCartItem(Long productId) {

        String userName = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new UserNotFoundException(userName));

        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new CartNotFoundForUserException(user.getEmail()));

        boolean deleted = cart.getCartItemList().removeIf(item -> item.getProduct().getId().equals(productId));

        if(!deleted){
            throw new CartItemNotFoundException(productId);
        }

        BigDecimal newTotal = cart.getCartItemList().stream()
                .map(cartItem -> cartItem.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalPrice(newTotal);

        Cart savedCart = cartRepository.save(cart);

        List<CartItemResponse> cartItemResponses = mapCartItemsToDto(cart);

        return mapToCartResponse(cartItemResponses, cart);
    }

    @Override
    public void deleteUserCart() {
        String userName = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(userName).orElseThrow(() -> new UserNotFoundException(userName));

        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new CartNotFoundForUserException(userName));

        cartRepository.delete(cart);
    }

    private Cart createNewCart(User user) {
        return Cart.builder()
                .user(user)
                .cartItemList(new ArrayList<>())
                .totalPrice(BigDecimal.ZERO)
                .build();
    }

    private List<CartItemResponse> mapCartItemsToDto (Cart cart){
        return cart.getCartItemList().stream()
                .map(cartItem -> CartItemResponse.builder()
                        .productName(cartItem.getProduct().getName())
                        .quantity(cartItem.getQuantity())
                        .price(cartItem.getProduct().getPrice())
                        .subtotal(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                        .build())
                .toList();
    }

    private CartResponse mapToCartResponse (List<CartItemResponse> cartItemResponseList, Cart cart){
        return CartResponse.builder()
                .items(cartItemResponseList)
                .total(cart.getTotalPrice())
                .build();
    }
}
