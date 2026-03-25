package com.tanmay.buyit.exception;

public class CartNotFoundForUserException extends RuntimeException{

    public CartNotFoundForUserException(String user){
        super("The cart for user : "+user +"doesn't exist");
    }
}
