package com.tanmay.buyit.controller;

import com.tanmay.buyit.dto.OrderResponse;
import com.tanmay.buyit.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
@Tag(name = "Order APIs", description = "Operations related to placing Order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder (){
        return new ResponseEntity<OrderResponse>(orderService.placeOrderForUser(), HttpStatus.OK);
    }
}
