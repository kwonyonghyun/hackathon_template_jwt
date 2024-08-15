package com.example.hackathon_template_jwt.exception;

public class InvalidCredentialsException extends HackathonException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}