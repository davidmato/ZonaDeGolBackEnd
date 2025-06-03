package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Jornada;
import com.example.zonadegolbackend.entity.Temporada;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Integer> {

    List<Jornada> findByEquipoLocal_IdOrEquipoVisitante_Id(Integer localId, Integer visitanteId);

    List<Jornada> findByEquipoLocalOrEquipoVisitante(Equipo equipoLocal, Equipo equipoVisitante);

    @Query("""
    SELECT j FROM Jornada j
    WHERE (j.equipoLocal = :equipo OR j.equipoVisitante = :equipo)
      AND j.temporada = :temporada
    ORDER BY j.fecha DESC
    """)
    List<Jornada> findLast5ByEquipoAndTemporada(@Param("equipo") Equipo equipo, @Param("temporada") Temporada temporada, Pageable pageable);

    List<Jornada> findByArbitro_Id(Integer arbitroId);
}
