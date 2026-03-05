package com.tanmay.buyit.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(){
        super("This product doesn't exist");
    }
}
