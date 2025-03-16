package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.EquipoLiga;
import com.example.zonadegolbackend.entity.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigaEquipoRepository extends JpaRepository<EquipoLiga, Integer> {

    List<EquipoLiga> findByTemporada(Temporada temporada);
}
