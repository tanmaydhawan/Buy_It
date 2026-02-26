package com.tanmay.buyit.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String email){
        super("User with this email already exists: "+email);
    }
}
