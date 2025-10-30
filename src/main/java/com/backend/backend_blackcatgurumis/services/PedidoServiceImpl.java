package com.backend.backend_blackcatgurumis.services;

import com.backend.backend_blackcatgurumis.dto.CrearPedidoRequest;
import com.backend.backend_blackcatgurumis.dto.ItemPedidoDto;
import com.backend.backend_blackcatgurumis.entities.ItemPedido;
import com.backend.backend_blackcatgurumis.entities.Pedido;
import com.backend.backend_blackcatgurumis.entities.Producto;
import com.backend.backend_blackcatgurumis.entities.Usuario;
import com.backend.backend_blackcatgurumis.repositories.PedidoRepository;
import com.backend.backend_blackcatgurumis.repositories.ProductoRepository;
import com.backend.backend_blackcatgurumis.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    // Inyecta los repositorios
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;
    
    // Inyecta la INTERFAZ del servicio de producto
    @Autowired
    private ProductoService productoService; 

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public List<Pedido> obtenerPedidosPorUsuario(String emailUsuario) {
        // Busca al usuario por su email
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Usa el método personalizado del repositorio
        return pedidoRepository.findByUsuario(usuario);
    }

    @Override
    @Transactional // Revierte la transacción si algo falla
    public Pedido crearPedido(CrearPedidoRequest pedidoRequest, String emailUsuario) {
        
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        long total = 0;
        List<ItemPedido> itemsDelPedido = new ArrayList<>();

        // Crea la entidad Pedido principal
        Pedido nuevoPedido = Pedido.builder()
                .usuario(usuario)
                .fechaCreacion(LocalDateTime.now())
                .estado("PENDIENTE")
                .build();

        // Itera sobre los items del carrito
        for (ItemPedidoDto itemDto : pedidoRequest.getItems()) {
            
            Producto producto = productoRepository.findById(itemDto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: ID " + itemDto.getProductoId()));
            
            // Llama al servicio de producto para descontar stock
            productoService.actualizarStock(producto.getId(), itemDto.getCantidad());
            
            // Calcula el total de los productos
            total += (producto.getPrecio() * itemDto.getCantidad());

            // Crea la entidad ItemPedido
            ItemPedido itemPedido = ItemPedido.builder()
                    .producto(producto)
                    .pedido(nuevoPedido)
                    .cantidad(itemDto.getCantidad())
                    .precioUnitario(producto.getPrecio())
                    .build();
            
            itemsDelPedido.add(itemPedido);
        }

        // Suma el costo de envío al total
        Long costoEnvio = pedidoRequest.getCostoEnvio();
        if (costoEnvio != null) {
            total += costoEnvio;
        }

        // Asigna el total y los items al pedido
        nuevoPedido.setTotal(total);
        nuevoPedido.setItems(itemsDelPedido);

        // Guarda el pedido y sus items
        return pedidoRepository.save(nuevoPedido);
    }

    @Override
    @Transactional(readOnly = true)
    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findByIdWithItems(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Pedido actualizarEstadoPedido(Long id, String estado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
        pedido.setEstado(estado);
        return pedidoRepository.save(pedido);
    }
}