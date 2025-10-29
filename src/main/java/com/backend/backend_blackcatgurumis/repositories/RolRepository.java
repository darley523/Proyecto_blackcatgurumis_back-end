package com.backend.backend_blackcatgurumis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backend_blackcatgurumis.entities.Rol;


@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{

}
