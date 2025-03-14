package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.EquipoLiga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigaEquipoRepository extends JpaRepository<EquipoLiga, Integer> {
}
