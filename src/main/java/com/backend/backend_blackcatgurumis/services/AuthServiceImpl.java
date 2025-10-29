package com.backend.backend_blackcatgurumis.services;

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

// Anotación que marca esta clase como un Servicio de Spring
@Service
// Inyecta las dependencias finales vía constructor
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    // Inyección de dependencias
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(LoginRequest request) {
        // Autentica al usuario con Spring Security
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Busca al usuario en la base de datos
        Usuario user = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado después de autenticación"));
        
        // Genera el token JWT
        String token = jwtService.generateToken(user);
        
        return AuthResponse.builder().token(token).build();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        // Valida si el email ya está en uso
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Busca el rol de usuario, o lo crea si no existe
        Rol userRol = rolRepository.findByNombre("ROLE_USER")
                .orElseGet(() -> {
                    Rol newRol = Rol.builder().nombre("ROLE_USER").build();
                    return rolRepository.save(newRol);
                });

        // Crea la nueva entidad Usuario
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .telefono(request.getTelefono())
                .rut(request.getRut())
                .roles(List.of(userRol)) 
                .build();

        // Guarda el usuario
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Genera y devuelve el token
        String token = jwtService.generateToken(usuarioGuardado);
        
        return AuthResponse.builder().token(token).build();
    }
}