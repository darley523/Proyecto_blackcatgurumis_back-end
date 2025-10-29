package com.backend.backend_blackcatgurumis.services;

import com.backend.backend_blackcatgurumis.entities.Producto;
import java.util.List;

public interface ProductoService {

    List<Producto> listarTodos();

    Producto obtenerPorId(Long id);

    Producto crearProducto(Producto producto);

    Producto actualizarProducto(Long id, Producto productoDetails);

    void eliminarProducto(Long id);
    
    void actualizarStock(Long productoId, Integer cantidadComprada);
}