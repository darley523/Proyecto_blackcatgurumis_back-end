package com.backend.backend_blackcatgurumis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backend_blackcatgurumis.entities.Producto;

//JpaRepository extiene de crudrepository y tiene mas metodos
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
