package com.unibuc.laborator.exception;

public class AuthFailedException extends RuntimeException{
    public AuthFailedException(){
        super("Invalid username or password provided");
    }
}
