package com.example.hackathon_template_jwt.exception;

public class InvalidTokenException extends HackathonException {
    public InvalidTokenException(String message) {
        super(message);
    }
}