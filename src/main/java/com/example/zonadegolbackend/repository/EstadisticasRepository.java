package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.dtos.EstadisticasLigaTemporadaDTO;
import com.example.zonadegolbackend.entity.Estadisticas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadisticasRepository extends JpaRepository<Estadisticas,Integer> {
    @Query("SELECT e FROM Estadisticas e WHERE e.jugador.id = :jugadorId")
    List<Estadisticas> findByJugadorId(@Param("jugadorId") Integer jugadorId);

    @Query("SELECT e FROM Estadisticas e WHERE e.jugador.id = :jugadorId ORDER BY e.temporada.fechaFin DESC")
    List<Estadisticas> findLatestByJugadorId(@Param("jugadorId") Integer jugadorId);

    @Query("SELECT e FROM Estadisticas e WHERE e.temporada.id = :temporadaId AND e.jugador.equipo.liga.id = :ligaId")
    List<Estadisticas> findByTemporadaAndLiga(@Param("temporadaId") Integer temporadaId, @Param("ligaId") Integer ligaId);


    //Hecho por DTO
    @Query("SELECT new com.example.zonadegolbackend.dtos.EstadisticasLigaTemporadaDTO(e.partidosJugados, e.goles, e.asistencias, e.tarjetasAmarillas, e.tarjetasRojas, e.porteriaCero, e.jugador.nombre, e.jugador.apellido) " +
            "FROM Estadisticas e WHERE e.temporada.id = :temporadaId AND e.jugador.equipo.liga.id = :ligaId")
    List<EstadisticasLigaTemporadaDTO> findByTemporadaAndLigaDTO(@Param("temporadaId") Integer temporadaId, @Param("ligaId") Integer ligaId);

}
