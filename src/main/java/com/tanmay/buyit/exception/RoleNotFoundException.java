package com.tanmay.buyit.exception;

public class RoleNotFoundException extends RuntimeException{

    public RoleNotFoundException(String role){
        super("The defult role doesn't exist: "+role);
    }
}
