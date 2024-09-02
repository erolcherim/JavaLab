package com.unibuc.laborator.controller;

import com.unibuc.laborator.model.Organisation;
import com.unibuc.laborator.model.User;
import com.unibuc.laborator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id){
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
