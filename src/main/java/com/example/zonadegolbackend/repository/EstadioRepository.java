package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Estadio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadioRepository extends JpaRepository<Estadio, Integer> {

    @Query("SELECT e FROM Estadio e WHERE e.nombre = :nombre")
    Optional<Estadio> findByNombre(@Param("nombre") String nombre);
}

