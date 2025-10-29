package com.backend.backend_blackcatgurumis.controllers;

import com.backend.backend_blackcatgurumis.entities.Usuario;
import com.backend.backend_blackcatgurumis.services.UsuarioService; 
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/usuarios") // Ruta base protegida para Admin
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    // Inyecta la interfaz del servicio de usuarios
    private final UsuarioService usuarioService;

    /**
     * Endpoint para listar todos los usuarios (Admin).
     * GET /api/admin/usuarios
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Endpoint para obtener un usuario por ID (Admin).
     * GET /api/admin/usuarios/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Endpoint para eliminar un usuario (Admin).
     * DELETE /api/admin/usuarios/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}