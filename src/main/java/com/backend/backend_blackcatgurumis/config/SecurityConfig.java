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
import org.springframework.web.cors.CorsConfiguration; // IMPORTAR
import org.springframework.web.cors.CorsConfigurationSource; // IMPORTAR
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // IMPORTAR
import java.util.Arrays; // IMPORTAR

import com.backend.backend_blackcatgurumis.config.filters.JwtAuthenticationFilter;
import com.backend.backend_blackcatgurumis.services.UserDetailsServiceImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // --- BEAN PARA CONFIGURACIÓN GLOBAL DE CORS ---
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Origen permitido (tu frontend React)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        // Métodos permitidos (IMPORTANTE incluir OPTIONS)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Cabeceras permitidas (IMPORTANTE incluir Authorization)
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplicar esta configuración a todas las rutas bajo /api/
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }

    // --- CADENA DE FILTROS DE SEGURIDAD  ---
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. APLICAR LA CONFIGURACIÓN GLOBAL DE CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // 2. Deshabilitar CSRF
            .csrf(AbstractHttpConfigurer::disable)

            // 3. Sesión sin estado
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 4. Configurar la autorización
            .authorizeHttpRequests(auth -> auth

                // RUTAS PÚBLICAS
                .requestMatchers(
                    "/api/auth/**",
                    "/api/productos/**",
                    "/api/categorias/**"
                ).permitAll()

                // PERMISO EXPLÍCITO PARA BORRAR USUARIOS (ADMIN)
                .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/api/admin/usuarios/**").hasAuthority("ROLE_ADMIN")

                // RUTAS PROTEGIDAS PARA ADMIN
                .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")

                // RUTAS PROTEGIDAS (PEDIDOS)
                .requestMatchers("/api/pedidos/**").authenticated()

                // Cualquier otra petición autenticada
                .anyRequest().authenticated()
            )

            // 5. AÑADIR EL FILTRO DE JWT
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
