package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.EquipoJugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoJugadorRepository extends JpaRepository<EquipoJugador,Integer> {
}
