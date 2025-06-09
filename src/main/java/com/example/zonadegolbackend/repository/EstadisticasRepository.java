package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.dtos.EstadisticasLigaTemporadaDTO;
import com.example.zonadegolbackend.entity.Estadisticas;
import com.example.zonadegolbackend.entity.Jugador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    List<Estadisticas> findByJugador(Jugador jugador);

    @Query("""
    SELECT e FROM Estadisticas e
    ORDER BY e.goles DESC
""")
    Page<Estadisticas> findTopScorers(Pageable pageable);

    @Query("""
    SELECT e FROM Estadisticas e
    ORDER BY e.asistencias DESC
""")
    Page<Estadisticas> findTopAssistants(Pageable pageable);


    @Query("""
    SELECT e FROM Estadisticas e
    WHERE e.jugador.posicion = com.example.zonadegolbackend.enums.Posicion.PORTERO
    ORDER BY e.porteriaCero DESC
    """)
    Page<Estadisticas> findTop5GoalkeepersWithMostCleanSheets(Pageable pageable);

    // Mayor goleador del equipo
    @Query("SELECT e FROM Estadisticas e " +
            "WHERE e.jugador.equipo.id = :equipoId " +
            "ORDER BY e.goles DESC LIMIT 1")
    Estadisticas findTopScorerByEquipo(@Param("equipoId") Integer equipoId);

    // Delantero con más goles
    @Query("SELECT e FROM Estadisticas e " +
            "WHERE e.jugador.equipo.id = :equipoId " +
            "AND e.jugador.posicion = com.example.zonadegolbackend.enums.Posicion.DELANTERO " +
            "ORDER BY e.goles DESC LIMIT 1")
    Estadisticas findTopScoringForward(@Param("equipoId") Integer equipoId);

    // Mayor asistente
    @Query("SELECT e FROM Estadisticas e " +
            "WHERE e.jugador.equipo.id = :equipoId " +
            "ORDER BY e.asistencias DESC LIMIT 1")
    Estadisticas findTopAssistant(@Param("equipoId") Integer equipoId);

    // Jugador más expulsado
    @Query("SELECT e FROM Estadisticas e " +
            "WHERE e.jugador.equipo.id = :equipoId " +
            "ORDER BY e.tarjetasRojas DESC LIMIT 1")
    Estadisticas findMostSentOffPlayer(@Param("equipoId") Integer equipoId);

    // Suma de porterías a cero por defensas
    @Query("SELECT SUM(e.porteriaCero) FROM Estadisticas e " +
            "WHERE e.jugador.equipo.id = :equipoId " +
            "AND e.jugador.posicion = com.example.zonadegolbackend.enums.Posicion.PORTERO")
    Integer countCleanSheetsByDefenders(@Param("equipoId") Integer equipoId);


}
