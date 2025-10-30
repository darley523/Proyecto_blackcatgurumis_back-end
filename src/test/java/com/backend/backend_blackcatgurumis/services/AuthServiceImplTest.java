package com.backend.backend_blackcatgurumis.services;

import com.backend.backend_blackcatgurumis.dto.AuthResponse;
import com.backend.backend_blackcatgurumis.dto.LoginRequest;
import com.backend.backend_blackcatgurumis.dto.RegisterRequest;
import com.backend.backend_blackcatgurumis.entities.Rol;
import com.backend.backend_blackcatgurumis.entities.Usuario;
import com.backend.backend_blackcatgurumis.repositories.RolRepository;
import com.backend.backend_blackcatgurumis.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RolRepository rolRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private Authentication authentication; // Mock para el objeto Authentication

    @InjectMocks
    private AuthServiceImpl authService;

    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;
    private Usuario testUser;
    private Rol userRol;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        registerRequest = new RegisterRequest();
        registerRequest.setNombre("Test User");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");
        registerRequest.setTelefono("123456789");
        registerRequest.setRut("12345678-9");

        userRol = Rol.builder().id(1).nombre("ROLE_USER").build();

        testUser = Usuario.builder()
                .id(1L)
                .nombre("Test User")
                .email("test@example.com")
                .password("encodedPassword")
                .telefono("123456789")
                .rut("12345678-9")
                .roles(List.of(userRol))
                .build();
    }

    @Test
    void testLogin_Success() {
        // Given
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication); // Simula autenticación exitosa
        when(usuarioRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(testUser));
        when(jwtService.generateToken(testUser)).thenReturn("mockedJwtToken");

        // When
        AuthResponse response = authService.login(loginRequest);

        // Then
        assertNotNull(response);
        assertEquals("mockedJwtToken", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioRepository).findByEmail(loginRequest.getEmail());
        verify(jwtService).generateToken(testUser);
    }

    @Test
    void testRegister_Success_RoleExists() {
        // Given
        when(usuarioRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(rolRepository.findByNombre("ROLE_USER")).thenReturn(Optional.of(userRol));
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(testUser);
        when(jwtService.generateToken(testUser)).thenReturn("mockedJwtToken");

        // When
        AuthResponse response = authService.register(registerRequest);

        // Then
        assertNotNull(response);
        assertEquals("mockedJwtToken", response.getToken());
        verify(usuarioRepository).findByEmail(registerRequest.getEmail());
        verify(rolRepository).findByNombre("ROLE_USER");
        verify(passwordEncoder).encode(registerRequest.getPassword());
        verify(usuarioRepository).save(any(Usuario.class));
        verify(jwtService).generateToken(testUser);
        verify(rolRepository, never()).save(any(Rol.class)); // No debería guardar el rol si ya existe
    }
}