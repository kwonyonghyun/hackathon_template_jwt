package com.example.hackathon_template_jwt.exception;

public class TokenNotFoundException extends HackathonException {
    public TokenNotFoundException(String message) {
        super(message);
    }
}