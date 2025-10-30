package com.backend.backend_blackcatgurumis.controllers;

import com.backend.backend_blackcatgurumis.entities.Usuario;
import com.backend.backend_blackcatgurumis.services.UsuarioService; 
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/admin/usuarios") // Ruta base protegida para Admin
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@SecurityRequirement(name = "token")
public class UsuarioController {

    // Inyecta la interfaz del servicio de usuarios
    private final UsuarioService usuarioService;

    /**
     * Endpoint para listar todos los usuarios (Admin).
     * GET /api/admin/usuarios
     */
    @Operation(summary = "Lista todos los usuarios (solo administradores)")
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Endpoint para obtener un usuario por ID (Admin).
     * GET /api/admin/usuarios/{id}
     */
    @Operation(summary = "Obtiene un usuario por su ID (solo administradores)")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Endpoint para eliminar un usuario (Admin).
     * DELETE /api/admin/usuarios/{id}
     */
    @Operation(summary = "Elimina un usuario por su ID (solo administradores)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}