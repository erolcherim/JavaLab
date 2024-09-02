package com.unibuc.laborator.controller;

import com.unibuc.laborator.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    @ResponseBody
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
