package com.backend.backend_blackcatgurumis.services;

import com.backend.backend_blackcatgurumis.dto.CrearPedidoRequest;
import com.backend.backend_blackcatgurumis.entities.Pedido;
import java.util.List;

public interface PedidoService {

    List<Pedido> obtenerTodosLosPedidos();

    List<Pedido> obtenerPedidosPorUsuario(String emailUsuario);

    Pedido crearPedido(CrearPedidoRequest pedidoRequest, String emailUsuario);
}