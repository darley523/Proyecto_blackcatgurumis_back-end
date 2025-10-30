package com.backend.backend_blackcatgurumis.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend_blackcatgurumis.dto.AuthResponse;
import com.backend.backend_blackcatgurumis.dto.LoginRequest;
import com.backend.backend_blackcatgurumis.dto.RegisterRequest;
import com.backend.backend_blackcatgurumis.services.AuthService; 

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth") // Define la ruta base para este controlador
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // Permite llamadas desde React
public class AuthController {


    // Inyecta la interfaz del servicio de autenticación
    private final AuthService authService;

    /**
     * Endpoint para iniciar sesión
     * POST /api/auth/login
     */
    @Operation(summary = "Inicia sesión de un usuario y devuelve un token JWT")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Llama al servicio para procesar el login
        AuthResponse response = authService.login(request);
        
        // Devuelve la respuesta (el token) al frontend
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para registrar un nuevo usuario
     * POST /api/auth/register
     */
    @Operation(summary = "Registra un nuevo usuario y devuelve un token JWT")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        // Llama al servicio para procesar el registro
        AuthResponse response = authService.register(request);

        // Devuelve la respuesta (el token) al frontend
        return ResponseEntity.ok(response);
    }
}