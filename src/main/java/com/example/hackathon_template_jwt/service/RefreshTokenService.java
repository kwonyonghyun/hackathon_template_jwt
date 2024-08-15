package com.example.hackathon_template_jwt.service;

import com.example.hackathon_template_jwt.domain.RefreshToken;
import com.example.hackathon_template_jwt.exception.TokenNotFoundException;
import com.example.hackathon_template_jwt.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new TokenNotFoundException("Refresh token not found"));
    }

    public void saveRefreshToken(Long userId, String refreshToken) {
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByUserId(userId)
                .map(token -> token.update(refreshToken))
                .orElse(new RefreshToken(null, userId, refreshToken));

        refreshTokenRepository.save(refreshTokenEntity);
    }
}