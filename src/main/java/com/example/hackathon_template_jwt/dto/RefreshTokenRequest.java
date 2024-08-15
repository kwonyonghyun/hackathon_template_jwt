package com.example.hackathon_template_jwt.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
