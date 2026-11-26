package com.sensormonitor.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sensormonitor.backend.dto.AuthResponse;
import com.sensormonitor.backend.dto.LoginRequest;
import com.sensormonitor.backend.dto.RegisterRequest;
import com.sensormonitor.backend.service.AuthService;
import com.sensormonitor.backend.service.TokenBlacklistService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthController(
            AuthService authService,
            TokenBlacklistService tokenBlacklistService) {

        this.authService = authService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {

        authService.registerUser(
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok(
                new AuthResponse(
                        null,
                        "Registration successful"
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {

        AuthResponse response =
                authService.loginUser(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(
            HttpServletRequest request) {

        String authorizationHeader =
                request.getHeader("Authorization");

        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {

            throw new IllegalArgumentException(
                    "Authorization token is required"
            );
        }

        String token =
                authorizationHeader.substring(7);

        tokenBlacklistService.blacklistToken(token);

        return ResponseEntity.ok(
                new AuthResponse(
                        null,
                        "Logout successful"
                )
        );
    }
}