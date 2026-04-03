package com.tanmay.buyit.service;

import com.tanmay.buyit.dto.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrderResponse placeOrderForUser();
}
