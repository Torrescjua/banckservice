package com.javeriana.edu.banco.banckservice.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.javeriana.edu.banco.banckservice.model.AuthResponse;
import com.javeriana.edu.banco.banckservice.model.LoginRequest;
import com.javeriana.edu.banco.banckservice.model.RegisterRequest;
import com.javeriana.edu.banco.banckservice.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
