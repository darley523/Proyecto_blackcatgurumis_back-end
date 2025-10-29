package com.backend.backend_blackcatgurumis.services;

import com.backend.backend_blackcatgurumis.entities.Producto;
import com.backend.backend_blackcatgurumis.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    // Inyecta el repositorio de productos
    @Autowired
    private ProductoRepository productoRepository;
    
    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizarProducto(Long id, Producto productoDetails) {
        // Busca el producto existente
        Producto producto = obtenerPorId(id);
        
        // Actualiza todos los campos
        producto.setNombre(productoDetails.getNombre());
        producto.setDescripcion(productoDetails.getDescripcion());
        producto.setPrecio(productoDetails.getPrecio());
        producto.setActivo(productoDetails.getActivo());
        producto.setStock(productoDetails.getStock());
        producto.setCategoria(productoDetails.getCategoria());
        
        // Guarda los cambios
        return productoRepository.save(producto);
    }

    @Override
    public void eliminarProducto(Long id) {
        Producto producto = obtenerPorId(id);
        productoRepository.delete(producto);
    }
    
    @Override
    public void actualizarStock(Long productoId, Integer cantidadComprada) {
        Producto producto = obtenerPorId(productoId);
        
        // Valida que haya stock suficiente
        if (producto.getStock() < cantidadComprada) {
            throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
        }
        
        // Resta el stock y guarda
        producto.setStock(producto.getStock() - cantidadComprada);
        productoRepository.save(producto);
    }
}