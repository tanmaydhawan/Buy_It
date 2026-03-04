package com.tanmay.buyit.exception;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException(){
        super("This category doesn't exist: ");
    }
}
