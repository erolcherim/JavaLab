package com.unibuc.laborator.exception;

public class NotFoundCustomException extends RuntimeException{
    public NotFoundCustomException(){
        super("Entity Not Found");
    }
}
