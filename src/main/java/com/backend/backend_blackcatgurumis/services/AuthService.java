package com.backend.backend_blackcatgurumis.services;

import com.backend.backend_blackcatgurumis.services.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.backend_blackcatgurumis.dto.AuthResponse;
import com.backend.backend_blackcatgurumis.dto.LoginRequest;
import com.backend.backend_blackcatgurumis.dto.RegisterRequest;
import com.backend.backend_blackcatgurumis.entities.Rol;
import com.backend.backend_blackcatgurumis.entities.Usuario;
import com.backend.backend_blackcatgurumis.repositories.RolRepository;
import com.backend.backend_blackcatgurumis.repositories.UsuarioRepository;

import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        // Spring Security se encarga de validar email y contraseña
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );


        Usuario user = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado después de autenticación"));
        
        // Generamos el token
        String token = jwtService.generateToken(user);
        
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse register(RegisterRequest request) {
        // Verificar si el email ya existe
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Buscar el rol "ROLE_USER".
        Rol userRol = rolRepository.findByNombre("ROLE_USER")
                .orElseGet(() -> {
                    Rol newRol = Rol.builder().nombre("ROLE_USER").build();
                    return rolRepository.save(newRol);
                });

        // 3. Crear el nuevo usuario
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(userRol)) // Asignar el rol
                .build();

        //Guardar el usuario en la BD
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        //Generar y devolver el token
        String token = jwtService.generateToken(usuarioGuardado);
        
        return AuthResponse.builder().token(token).build();
    }
}