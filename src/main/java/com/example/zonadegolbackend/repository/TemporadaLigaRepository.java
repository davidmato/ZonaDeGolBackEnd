package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.TemporadaLiga;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface TemporadaLigaRepository extends JpaRepository<TemporadaLiga,Integer> {
    TemporadaLiga findByTemporadaIdAndLigaId(Integer temporadaId, Integer ligaId);
}
