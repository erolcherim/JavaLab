package com.unibuc.laborator.exception;

public class DuplicateException extends RuntimeException{
    public DuplicateException(String entityType){
        super("Duplicate property does not allow resource creation: " + entityType);
    }
}