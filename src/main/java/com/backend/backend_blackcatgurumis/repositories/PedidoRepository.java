package com.backend.backend_blackcatgurumis.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backend_blackcatgurumis.entities.Pedido;
import com.backend.backend_blackcatgurumis.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

    List<Pedido> findByUsuario(Usuario usuario);

    @Query("SELECT p FROM Pedido p JOIN FETCH p.items i LEFT JOIN FETCH i.producto WHERE p.id = :id")
    Optional<Pedido> findByIdWithItems(@Param("id") Long id);

}
