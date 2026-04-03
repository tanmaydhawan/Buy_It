package com.tanmay.buyit.controller;

import com.tanmay.buyit.dto.OrderResponse;
import com.tanmay.buyit.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder (){
        return new ResponseEntity<OrderResponse>(orderService.placeOrderForUser(), HttpStatus.OK);
    }
}
