package com.backend.backend_blackcatgurumis.services;

import com.backend.backend_blackcatgurumis.entities.Categoria;
import com.backend.backend_blackcatgurumis.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    // Inyecta el repositorio de categorías
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria obtenerPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    }

    @Override
    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria actualizarCategoria(Long id, Categoria categoriaDetails) {
        // Busca la categoría existente
        Categoria categoria = obtenerPorId(id);
        
        // Actualiza los campos
        categoria.setNombre(categoriaDetails.getNombre());
        categoria.setDescripcion(categoriaDetails.getDescripcion());
        
        // Guarda los cambios
        return categoriaRepository.save(categoria);
    }

    @Override
    public void eliminarCategoria(Long id) {
        Categoria categoria = obtenerPorId(id);
        categoriaRepository.delete(categoria);
    }
}