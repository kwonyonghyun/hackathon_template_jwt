package com.example.hackathon_template_jwt.dto;

import lombok.Data;

@Data
public class AddUserRequest {
    private String email;
    private String password;
}
