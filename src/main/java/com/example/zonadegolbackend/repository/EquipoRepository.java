package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Entrenador;
import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Integer> {

    Equipo findByEntrenador(Entrenador entrenador);

    boolean existsByEntrenador(Entrenador entrenador);

    @Query("SELECT e FROM Equipo e WHERE e.liga.id = :ligaId")
    List<Equipo> findByLigaId(@Param("ligaId") Integer ligaId);

    @Query("SELECT e FROM Equipo e WHERE e.nombre = :nombre")
    Optional<Equipo> findByNombre(@Param("nombre") String nombre);

    Equipo findByEntrenador_Usuario(Usuario usuario);
}
