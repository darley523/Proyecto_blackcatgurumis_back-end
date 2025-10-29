package com.backend.backend_blackcatgurumis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.backend.backend_blackcatgurumis.services.UserDetailsServiceImpl; 
@Configuration 
@EnableWebSecurity
public class SecurityConfig {
    // Permitir CORS en todos los endpoints /api/** para el frontend en localhost:5173
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
                registry.addMapping("/api/usuarios/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
                registry.addMapping("/api/users/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    // Spring inyectará automáticamente UserDetailsServiceImpl aquí.
    private final UserDetailsService userDetailsService;

    // CONSTRUCTOR PARA INYECCIÓN DE DEPENDENCIAS 
    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    // --- Componentes ---
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Spring usará automáticamente el UserDetailsService y el PasswordEncoder.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // --- CADENA DE FILTROS DE SEGURIDAD  ---
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilitar CSRF (necesario para APIs REST con JWT)
            .csrf(AbstractHttpConfigurer::disable)
            
            // Definir la política de creación de sesión como STATELESS (sin estado)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Se elimina la línea .authenticationProvider() ya que Spring lo descubre automáticamente.
            
            // Configurar la autorización de peticiones
            .authorizeHttpRequests(auth -> auth
                
                // RUTAS PÚBLICAS
                .requestMatchers(
                    "/api/auth/**",       // Login y Register
                    "/api/productos/**",  
                    "/api/categorias/**"
                ).permitAll()
                
                // RUTAS PROTEGIDAS PARA ADMINISTRADOR
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                
                // RUTAS PROTEGIDAS (PEDIDOS)
                .requestMatchers("/api/pedidos/**").authenticated()

                // Cualquier otra petición debe estar autenticada
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
