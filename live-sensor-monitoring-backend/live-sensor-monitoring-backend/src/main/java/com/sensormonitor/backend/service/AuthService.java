package com.sensormonitor.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sensormonitor.backend.dto.AuthResponse;
import com.sensormonitor.backend.dto.LoginRequest;
import com.sensormonitor.backend.entity.User;
import com.sensormonitor.backend.exception.InvalidCredentialsException;
import com.sensormonitor.backend.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void registerUser(
            String email,
            String password) {

        if (userRepository.existsByEmail(email)) {

            throw new IllegalArgumentException(
                    "Email is already registered"
            );
        }

        String hashedPassword =
                passwordEncoder.encode(password);

        User user =
                new User(
                        email,
                        hashedPassword
                );

        userRepository.save(user);
    }

    public AuthResponse loginUser(
            LoginRequest request) {

        User user =
                userRepository
                        .findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new InvalidCredentialsException(
                                        "Invalid email or password"
                                )
                        );

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!passwordMatches) {

            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        String token =
                jwtService.generateToken(
                        user.getEmail()
                );

        return new AuthResponse(
                token,
                "Login successful"
        );
    }
}