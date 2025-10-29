package com.backend.backend_blackcatgurumis.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backend_blackcatgurumis.entities.Pedido;
import com.backend.backend_blackcatgurumis.entities.Usuario;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

    List<Pedido> findByUsuario(Usuario usuario);

}
