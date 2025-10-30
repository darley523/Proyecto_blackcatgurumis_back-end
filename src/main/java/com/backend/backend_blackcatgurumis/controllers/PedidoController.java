package com.backend.backend_blackcatgurumis.controllers;

import com.backend.backend_blackcatgurumis.dto.CrearPedidoRequest;
import com.backend.backend_blackcatgurumis.entities.Pedido;
import com.backend.backend_blackcatgurumis.services.PedidoService; 
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; // Importa Authentication
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api") // Define la ruta base
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@SecurityRequirement(name = "token")
public class PedidoController {

    // Inyecta la interfaz del servicio de pedidos
    private final PedidoService pedidoService;

    // --- Endpoints para Usuarios Logueados ---

    /**
     * Endpoint para crear un nuevo pedido (Usuario logueado).
     * POST /api/pedidos
     */
    @Operation(summary = "Crea un nuevo pedido para el usuario autenticado")
    @PostMapping("/pedidos")
    public ResponseEntity<Pedido> crearPedido(
            @RequestBody CrearPedidoRequest pedidoRequest, 
            Authentication authentication) { // Obtiene el usuario autenticado
        
        // Obtiene el email (username) del token
        String emailUsuario = authentication.getName();
        
        Pedido nuevoPedido = pedidoService.crearPedido(pedidoRequest, emailUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    /**
     * Endpoint para ver los pedidos del propio usuario (Usuario logueado).
     * GET /api/pedidos/mis-pedidos
     */
    @Operation(summary = "Obtiene los pedidos del usuario autenticado")
    @GetMapping("/pedidos/mis-pedidos")
    public ResponseEntity<List<Pedido>> obtenerPedidosPropios(Authentication authentication) {
        // Obtiene el email (username) del token
        String emailUsuario = authentication.getName();

        List<Pedido> pedidos = pedidoService.obtenerPedidosPorUsuario(emailUsuario);
        return ResponseEntity.ok(pedidos);
    }

    // --- Endpoints de Administración (para AdminOrders.jsx) ---

    /**
     * Endpoint para obtener todos los pedidos (Admin).
     * GET /api/admin/pedidos
     */
    @Operation(summary = "Obtiene todos los pedidos (solo administradores)")
    @GetMapping("/admin/pedidos")
    public ResponseEntity<List<Pedido>> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = pedidoService.obtenerTodosLosPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Obtiene un pedido por su ID (solo administradores)")
    @GetMapping("/admin/pedidos/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.obtenerPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }

    /**
     * Endpoint para actualizar el estado de un pedido (Admin).
     * PUT /api/admin/pedidos/{id}/estado
     */
    @Operation(summary = "Actualiza el estado de un pedido (solo administradores)")
    @PutMapping("/admin/pedidos/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstadoPedido(@PathVariable Long id, @RequestBody java.util.Map<String, String> request) {
        String nuevoEstado = request.get("estado");
        Pedido pedidoActualizado = pedidoService.actualizarEstadoPedido(id, nuevoEstado);
        return ResponseEntity.ok(pedidoActualizado);
    }
}