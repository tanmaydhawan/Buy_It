package com.tanmay.buyit.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String user){
        super("The User doesn't exist: "+user);
    }
}
