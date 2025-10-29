package com.backend.backend_blackcatgurumis.controllers;

import com.backend.backend_blackcatgurumis.entities.Producto;
import com.backend.backend_blackcatgurumis.services.ProductoService; 
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") // Define la ruta base
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // Permite la comunicación con React
public class ProductoController {

    // Inyecta la interfaz del servicio de productos
    private final ProductoService productoService;

    // --- Endpoints Públicos (para clientes) ---

    /**
     * Endpoint para obtener todos los productos.
     * GET /api/productos
     */
    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);
    }

    /**
     * Endpoint para obtener un producto por su ID.
     * GET /api/productos/{id}
     */
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Producto producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    // --- Endpoints de Administración (para AdminProducts.jsx) ---
    
    /**
     * Endpoint para crear un nuevo producto (Admin).
     * POST /api/admin/productos
     */
    @PostMapping("/admin/productos")
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crearProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    /**
     * Endpoint para actualizar un producto existente (Admin).
     * PUT /api/admin/productos/{id}
     */
    @PutMapping("/admin/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Long id, 
            @RequestBody Producto productoDetails) {
        Producto productoActualizado = productoService.actualizarProducto(id, productoDetails);
        return ResponseEntity.ok(productoActualizado);
    }

    /**
     * Endpoint para eliminar un producto (Admin).
     * DELETE /api/admin/productos/{id}
     */
    @DeleteMapping("/admin/productos/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}