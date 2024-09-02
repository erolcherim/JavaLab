package com.unibuc.laborator.service;

import com.unibuc.laborator.controller.UserResponse;
import com.unibuc.laborator.exception.NotFoundCustomException;
import com.unibuc.laborator.model.User;
import com.unibuc.laborator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserResponse getUserById(Integer id) {
        var fetchedUser = userRepository.findById(id).orElseThrow(NotFoundCustomException::new);

        return UserResponse.builder()
                .email(fetchedUser.getEmail()).
                firstName(fetchedUser.getUserInfo().getFirstName())
                .lastName(fetchedUser.getUserInfo().getLastName())
                .orgId(fetchedUser.getOrganisation().getId().toString())
                .build();
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username).orElseThrow(NotFoundCustomException::new);
    }
}
