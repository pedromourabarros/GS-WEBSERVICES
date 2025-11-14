package com.futureskill.api.controller;

import com.futureskill.api.dto.request.LoginRequest;
import com.futureskill.api.dto.request.RegisterRequest;
import com.futureskill.api.dto.response.AuthResponse;
import com.futureskill.api.dto.response.UsuarioResponse;
import com.futureskill.api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@Valid @RequestBody RegisterRequest request) {
        UsuarioResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}

