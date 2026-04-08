package com.tanmay.buyit.exception;

public class CartEmptyException extends RuntimeException{

    public CartEmptyException(){
        super("The cart is Empty");
    }
}
