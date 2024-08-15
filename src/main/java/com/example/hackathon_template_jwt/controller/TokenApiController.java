package com.example.hackathon_template_jwt.controller;


import com.example.hackathon_template_jwt.config.jwt.TokenProvider;
import com.example.hackathon_template_jwt.domain.User;
import com.example.hackathon_template_jwt.dto.LoginRequest;
import com.example.hackathon_template_jwt.dto.RefreshTokenRequest;
import com.example.hackathon_template_jwt.dto.TokenResponse;
import com.example.hackathon_template_jwt.service.TokenService;
import com.example.hackathon_template_jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse tokenResponse = tokenService.login(request);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        TokenResponse tokenResponse = tokenService.refreshToken(request);
        return ResponseEntity.ok(tokenResponse);
    }
}