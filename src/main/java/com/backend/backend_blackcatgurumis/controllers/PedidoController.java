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

@RestController
@RequestMapping("/api") // Define la ruta base
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PedidoController {

    // Inyecta la interfaz del servicio de pedidos
    private final PedidoService pedidoService;

    // --- Endpoints para Usuarios Logueados ---

    /**
     * Endpoint para crear un nuevo pedido (Usuario logueado).
     * POST /api/pedidos
     */
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
    @GetMapping("/admin/pedidos")
    public ResponseEntity<List<Pedido>> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = pedidoService.obtenerTodosLosPedidos();
        return ResponseEntity.ok(pedidos);
    }
}