package com.chatop.estate.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.estate.dtos.AuthDTO;
import com.chatop.estate.dtos.LoginDTO;
import com.chatop.estate.dtos.RegisterDTO;
import com.chatop.estate.dtos.UserDTO;
import com.chatop.estate.models.User;
import com.chatop.estate.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthDTO> register(@RequestBody RegisterDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody LoginDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> me() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = authService.findUserByEmail(email);
        return ResponseEntity.ok(authService.getCurrentUser(user));
    }
}
