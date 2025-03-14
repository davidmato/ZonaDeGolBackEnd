package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.EquipoTemporada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoTemporadaRepository extends JpaRepository<EquipoTemporada, Integer> {
}
