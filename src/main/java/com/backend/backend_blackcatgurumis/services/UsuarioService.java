package com.backend.backend_blackcatgurumis.services;

import com.backend.backend_blackcatgurumis.entities.Usuario;
import java.util.List;

// Define el contrato para el servicio de gestión de usuarios
public interface UsuarioService {

    List<Usuario> listarTodos();

    Usuario obtenerPorId(Long id);

    void eliminarUsuario(Long id);
}