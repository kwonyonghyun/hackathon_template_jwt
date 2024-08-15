package com.example.hackathon_template_jwt.service;

import com.example.hackathon_template_jwt.config.jwt.TokenProvider;
import com.example.hackathon_template_jwt.domain.RefreshToken;
import com.example.hackathon_template_jwt.domain.User;
import com.example.hackathon_template_jwt.dto.LoginRequest;
import com.example.hackathon_template_jwt.dto.RefreshTokenRequest;
import com.example.hackathon_template_jwt.dto.TokenResponse;
import com.example.hackathon_template_jwt.exception.InvalidCredentialsException;
import com.example.hackathon_template_jwt.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public TokenResponse login(LoginRequest request) {
        User user = userService.findByEmail(request.getEmail());
        validatePassword(request.getPassword(), user.getPassword());

        String accessToken = tokenProvider.generateAccessToken(user, Duration.ofHours(2));
        String refreshToken = tokenProvider.generateRefreshToken(user, Duration.ofDays(14));

        refreshTokenService.saveRefreshToken(user.getId(), refreshToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse refreshToken(RefreshTokenRequest request) {
        validateRefreshToken(request.getRefreshToken());

        RefreshToken refreshTokenEntity = refreshTokenService.findByRefreshToken(request.getRefreshToken());
        User user = userService.findById(refreshTokenEntity.getUserId());

        String newAccessToken = tokenProvider.generateAccessToken(user, Duration.ofHours(2));

        return new TokenResponse(newAccessToken, request.getRefreshToken());
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new InvalidCredentialsException("Invalid password");
        }
    }

    private void validateRefreshToken(String refreshToken) {
        if (!tokenProvider.validRefreshToken(refreshToken)) {
            throw new InvalidTokenException("Invalid refresh token");
        }
    }
}
