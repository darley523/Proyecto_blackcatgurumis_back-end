package com.backend.backend_blackcatgurumis.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend_blackcatgurumis.dto.AuthResponse;
import com.backend.backend_blackcatgurumis.dto.LoginRequest;
import com.backend.backend_blackcatgurumis.dto.RegisterRequest;
import com.backend.backend_blackcatgurumis.services.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth") // Todas las rutas de este controlador empiezan con /api/auth
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // Permite llamadas desde React en puerto 5173
public class AuthController {

    // Inyecta el servicio que creaste
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Llama al servicio para que haga el trabajo
        AuthResponse response = authService.login(request);
        
        // Devuelve la respuesta (el token) al frontend
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        // Llama al servicio para que haga el trabajo
        AuthResponse response = authService.register(request);

        // Devuelve la respuesta (el token) al frontend
        return ResponseEntity.ok(response);
    }
}