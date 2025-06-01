package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Clasificacion;
import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClasificacionRepository extends JpaRepository<Clasificacion, Integer> {

    List<Clasificacion> findByEquipo_Liga_IdAndTemporada_Id(Integer ligaId, Integer temporadaId);

    Clasificacion findByEquipoAndTemporada(Equipo equipo, Temporada temporada);

    @Query("SELECT c FROM Clasificacion c WHERE c.equipo.id = :idEquipo")
    List<Clasificacion> findByEquipoId(@Param("idEquipo") Integer idEquipo);

    List<Clasificacion> findByTemporadaIdOrderByPuestoAsc(Integer temporadaId);

}
