package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Clasificacion;
import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ClasificacionRepository extends JpaRepository<Clasificacion, Integer> {

    List<Clasificacion> findByEquipo_Liga_IdAndTemporada_Id(Integer ligaId, Integer temporadaId);

    Clasificacion findByEquipoAndTemporada(Equipo equipo, Temporada temporada);

    @Query("SELECT c FROM Clasificacion c WHERE c.equipo.id = :idEquipo")
    List<Clasificacion> findByEquipoId(@Param("idEquipo") Integer idEquipo);

    List<Clasificacion> findByTemporadaIdOrderByPuestoAsc(Integer temporadaId);

    @Query("SELECT SUM(c.golAFavor) AS totalGolesAFavor, SUM(c.golEnContra) AS totalGolesEnContra " +
            "FROM Clasificacion c " +
            "WHERE c.equipo.entrenador.id = :entrenadorId")
    Map<String, Long> findTotalGolesByEntrenadorId(@Param("entrenadorId") Integer entrenadorId);

    @Query("SELECT c FROM Clasificacion c WHERE c.temporada.id = :temporadaId AND c.equipo.liga.id = :ligaId ORDER BY c.puesto ASC")
    List<Clasificacion> findByTemporadaIdAndLigaIdOrderByPuestoAsc(@Param("temporadaId") Integer temporadaId, @Param("ligaId") Integer ligaId);


}
