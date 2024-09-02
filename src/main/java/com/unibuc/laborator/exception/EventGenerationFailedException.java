package com.unibuc.laborator.exception;

public class EventGenerationFailedException extends RuntimeException {
    public EventGenerationFailedException() {
        super("Event generation failed");
    }
}
