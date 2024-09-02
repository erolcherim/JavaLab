package com.unibuc.laborator.exception;

public class PasswordException extends RuntimeException{
    public PasswordException(){
        super("Password is invalid");
    }
}