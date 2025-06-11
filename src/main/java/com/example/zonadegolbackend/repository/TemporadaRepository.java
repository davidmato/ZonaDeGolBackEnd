package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemporadaRepository extends JpaRepository<Temporada, Integer> {

    @Query("SELECT t FROM Temporada t WHERE YEAR(t.fechaInicio) = :year")
    Temporada findByYear(@Param("year") int year);

    @Query("SELECT t FROM Temporada t ORDER BY t.fechaFin DESC")
    List<Temporada> findLatest();

    @Query("""
        SELECT tl.temporada
        FROM TemporadaLiga tl
        WHERE tl.liga.id = :ligaId
        ORDER BY tl.temporada.fechaInicio DESC
        """)
    List<Temporada> findTemporadasByLigaIdOrderByFechaInicioDesc(@Param("ligaId") Integer ligaId);

    default Temporada findUltimaTemporadaPorLigaId(Integer ligaId) {
        List<Temporada> temporadas = findTemporadasByLigaIdOrderByFechaInicioDesc(ligaId);
        return temporadas.isEmpty() ? null : temporadas.getFirst();
    }
}
