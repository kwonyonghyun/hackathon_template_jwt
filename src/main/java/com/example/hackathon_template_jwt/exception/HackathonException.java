package com.example.hackathon_template_jwt.exception;

public class HackathonException extends RuntimeException {
    public HackathonException(String message) {
        super(message);
    }

    public HackathonException(String message, Throwable cause) {
        super(message, cause);
    }
}