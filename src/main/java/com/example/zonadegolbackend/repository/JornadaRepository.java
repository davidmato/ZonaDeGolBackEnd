package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Integer> {

    List<Jornada> findByEquipoLocal_IdOrEquipoVisitante_Id(Integer localId, Integer visitanteId);

    List<Jornada> findByEquipoLocalOrEquipoVisitante(Equipo equipoLocal, Equipo equipoVisitante);
}
