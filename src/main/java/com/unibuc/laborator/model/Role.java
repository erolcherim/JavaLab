package com.unibuc.laborator.model;

public enum Role {
    USER("user"),
    ADMIN("admin");

    private final String role;

    Role(String role) {
        this.role = role;
    }
}
