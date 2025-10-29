package com.backend.backend_blackcatgurumis.services;

import com.backend.backend_blackcatgurumis.entities.Categoria;
import java.util.List;

public interface CategoriaService {

    List<Categoria> listarTodas();

    Categoria obtenerPorId(Long id);

    Categoria crearCategoria(Categoria categoria);

    Categoria actualizarCategoria(Long id, Categoria categoriaDetails);

    void eliminarCategoria(Long id);
}