package com.backend.backend_blackcatgurumis.services;

import com.backend.backend_blackcatgurumis.entities.Pedido;
import com.backend.backend_blackcatgurumis.entities.Usuario;
import com.backend.backend_blackcatgurumis.repositories.PedidoRepository;
import com.backend.backend_blackcatgurumis.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    @Transactional
    public void eliminarUsuario(Long id) {
        Usuario usuario = obtenerPorId(id);

        // Encontrar y eliminar los pedidos del usuario
        List<Pedido> pedidos = pedidoRepository.findByUsuario(usuario);
        if (pedidos != null && !pedidos.isEmpty()) {
            // Antes de eliminar los pedidos, debemos desasociar los items
            // o la cascada se encargará si está configurada.
            // Por seguridad, si no hay cascada, se puede hacer manualmente.
            // En este caso, asumimos que la relación Pedido -> ItemPedido tiene CascadeType.REMOVE
            pedidoRepository.deleteAll(pedidos);
        }

        // Ahora eliminar el usuario
        usuarioRepository.delete(usuario);
    }
}
