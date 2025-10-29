package com.backend.backend_blackcatgurumis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backend_blackcatgurumis.entities.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Metodo para buscar usuario por email
    Optional<Usuario> findByEmail(String email);
}
