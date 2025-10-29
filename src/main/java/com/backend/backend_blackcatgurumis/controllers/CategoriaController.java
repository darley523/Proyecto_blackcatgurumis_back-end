package com.backend.backend_blackcatgurumis.controllers;

import com.backend.backend_blackcatgurumis.entities.Categoria;
import com.backend.backend_blackcatgurumis.services.CategoriaService; 
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") // Define la ruta base
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CategoriaController {

    // Inyecta la interfaz del servicio de categorías
    private final CategoriaService categoriaService;

    // --- Endpoints Públicos ---

    /**
     * Endpoint para obtener todas las categorías.
     * GET /api/categorias
     */
    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> listarCategorias() {
        List<Categoria> categorias = categoriaService.listarTodas();
        return ResponseEntity.ok(categorias);
    }

    /**
     * Endpoint para obtener una categoría por su ID.
     * GET /api/categorias/{id}
     */
    @GetMapping("/categorias/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable Long id) {
        Categoria categoria = categoriaService.obtenerPorId(id);
        return ResponseEntity.ok(categoria);
    }

    // --- Endpoints de Administración ---

    /**
     * Endpoint para crear una nueva categoría (Admin).
     * POST /api/admin/categorias
     */
    @PostMapping("/admin/categorias")
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria) {
        Categoria nuevaCategoria = categoriaService.crearCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
    }

    /**
     * Endpoint para actualizar una categoría (Admin).
     * PUT /api/admin/categorias/{id}
     */
    @PutMapping("/admin/categorias/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(
            @PathVariable Long id, 
            @RequestBody Categoria categoriaDetails) {
        Categoria categoriaActualizada = categoriaService.actualizarCategoria(id, categoriaDetails);
        return ResponseEntity.ok(categoriaActualizada);
    }

    /**
     * Endpoint para eliminar una categoría (Admin).
     * DELETE /api/admin/categorias/{id}
     */
    @DeleteMapping("/admin/categorias/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}