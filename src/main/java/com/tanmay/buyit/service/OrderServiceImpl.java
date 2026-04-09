package com.tanmay.buyit.service;

import com.tanmay.buyit.dto.OrderItemResponse;
import com.tanmay.buyit.dto.OrderResponse;
import com.tanmay.buyit.entity.*;
import com.tanmay.buyit.enums.OrderStatus;
import com.tanmay.buyit.exception.CartEmptyException;
import com.tanmay.buyit.exception.CartNotFoundForUserException;
import com.tanmay.buyit.exception.ProductNotFoundException;
import com.tanmay.buyit.exception.UserNotFoundException;
import com.tanmay.buyit.repo.CartRepository;
import com.tanmay.buyit.repo.OrderRepository;
import com.tanmay.buyit.repo.ProductRepository;
import com.tanmay.buyit.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
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
        if(cart.getCartItemList() == null || cart.getCartItemList().isEmpty()){
            throw new CartEmptyException();
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
        for(CartItem ci : cart.getCartItemList()){
            Integer stock = ci.getProduct().getStock();
            Integer quantity = ci.getQuantity();

            if (stock== null || quantity == null || stock < quantity){
                throw new IllegalStateException("The required quantity cannot be fulfilled");
            }
        }

//  6. Calculate final price
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(CartItem ci : cart.getCartItemList()){
            BigDecimal price = ci.getProduct().getPrice();
            Integer quantity = ci.getQuantity();
            BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(quantity));
            totalAmount = totalAmount.add(itemTotal);
        }

//  7. Create order
        Order order = Order.builder()
                .user(user)
                .orderStatus(OrderStatus.PLACED)
                .totalAmount(totalAmount)
                .build();

//  8. Create order items
        List<OrderItem> orderItemList = new ArrayList<>();
        for(CartItem ci : cart.getCartItemList()){
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(ci.getProduct())
                    .price(ci.getProduct().getPrice())
                    .quantity(ci.getQuantity())
                    .build();

            orderItemList.add(orderItem);
        }
        order.setItems(orderItemList);
        orderRepository.save(order);


//  9. Reduce product stock
        for(CartItem ci : cart.getCartItemList()){
            Product product = ci.getProduct();
            Integer newStock = product.getStock() - ci.getQuantity();
            product.setStock(newStock);
        }
//  10. Clear cart
        cart.getCartItemList().clear();
        cartRepository.save(cart);

//  11. Return order response
        List<OrderItemResponse> orderItemResponseList = new ArrayList<>();
        for(OrderItem oi : order.getItems()){
            OrderItemResponse orderItem = OrderItemResponse.builder()
                    .productName(oi.getProduct().getName())
                    .quantity(oi.getQuantity())
                    .price(oi.getPrice())
                    .subTotal(oi.getPrice().multiply(BigDecimal.valueOf(oi.getQuantity())))
                    .build();
            orderItemResponseList.add(orderItem);
        }

        return OrderResponse.builder()
                .orderId(order.getId())
                .items(orderItemResponseList)
                .orderStatus(order.getOrderStatus())
                .createdAt(order.getCreatedAt())
                .totalAmount(totalAmount)
                .build();
    }
}
