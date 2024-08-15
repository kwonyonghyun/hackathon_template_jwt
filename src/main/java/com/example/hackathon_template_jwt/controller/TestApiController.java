package com.example.hackathon_template_jwt.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApiController {

    @GetMapping("/api/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("인증된 사용자만 접근 가능한 API 입니다.");
    }
}