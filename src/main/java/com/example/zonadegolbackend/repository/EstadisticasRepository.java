package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Estadisticas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadisticasRepository extends JpaRepository<Estadisticas,Integer> {
    @Query("SELECT e FROM Estadisticas e WHERE e.jugador.id = :jugadorId")
    List<Estadisticas> findByJugadorId(@Param("jugadorId") Integer jugadorId);

    @Query("SELECT e FROM Estadisticas e WHERE e.jugador.id = :jugadorId ORDER BY e.temporada.fechaFin DESC")
    List<Estadisticas> findLatestByJugadorId(@Param("jugadorId") Integer jugadorId);
}
