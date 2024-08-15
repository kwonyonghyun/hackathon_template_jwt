package com.example.hackathon_template_jwt.controller;


import com.example.hackathon_template_jwt.dto.AddUserRequest;
import com.example.hackathon_template_jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody AddUserRequest request) {
        Long userId = userService.save(request);
        return ResponseEntity.ok(userId);
    }
}