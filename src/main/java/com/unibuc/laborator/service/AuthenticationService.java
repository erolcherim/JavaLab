package com.unibuc.laborator.service;

import com.unibuc.laborator.controller.AuthRequest;
import com.unibuc.laborator.controller.AuthResponse;
import com.unibuc.laborator.controller.RegisterRequest;
import com.unibuc.laborator.exception.AuthFailedException;
import com.unibuc.laborator.exception.DuplicateException;
import com.unibuc.laborator.exception.PasswordException;
import com.unibuc.laborator.model.Role;
import com.unibuc.laborator.model.User;
import com.unibuc.laborator.model.UserInfo;
import com.unibuc.laborator.repository.UserInfoRepository;
import com.unibuc.laborator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private OrganisationService organisationService;

    public AuthResponse register(RegisterRequest registerRequest) {
        var userInfo = UserInfo.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .build();
        var user = User.builder()
                .email(registerRequest.getEmail().toLowerCase())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .userInfo(userInfo)
                .organisation(organisationService.getOrganisationById(Integer.valueOf(registerRequest.getOrgId())))
                .build();

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new PasswordException();
        } else if (!registerRequest.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            throw new PasswordException();
        } else if (userRepository.findByEmail(registerRequest.getEmail().toLowerCase()).isPresent()) {
            throw new DuplicateException("Email");
        } else {
            userRepository.save(user);
            userInfoRepository.save(userInfo);
            var jwtToken = jwtService.generateToken(user);
            return AuthResponse
                    .builder()
                    .token(jwtToken)
                    .build();
        }
    }

    public AuthResponse authenticate(AuthRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail().toLowerCase(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new AuthFailedException();
        }

        var user = userRepository.findByEmail(authenticationRequest.getEmail().toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException(authenticationRequest.getEmail()));
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
