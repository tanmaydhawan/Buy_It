package com.tanmay.buyit.exception;

public class CartItemNotFoundException extends RuntimeException{

    public CartItemNotFoundException(Long productId){
        super("The cart item doesn't exist for product id : "+productId);
    }
}
