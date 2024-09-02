package com.unibuc.laborator.controller;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    //return the token for postman / swagger
    private String token;
}
