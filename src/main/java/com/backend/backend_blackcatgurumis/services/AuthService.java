package com.backend.backend_blackcatgurumis.services;

import com.backend.backend_blackcatgurumis.dto.AuthResponse;
import com.backend.backend_blackcatgurumis.dto.LoginRequest;
import com.backend.backend_blackcatgurumis.dto.RegisterRequest;

public interface AuthService {
    
    AuthResponse login(LoginRequest request);
    
    AuthResponse register(RegisterRequest request);
}