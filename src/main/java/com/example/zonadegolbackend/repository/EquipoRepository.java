package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Entrenador;
import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Integer> {

    Equipo findByEntrenador(Entrenador entrenador);

}
