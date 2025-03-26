package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemporadaRepository extends JpaRepository<Temporada, Integer> {

    @Query("SELECT t FROM Temporada t WHERE YEAR(t.fechaInicio) = :year")
    Temporada findByYear(@Param("year") int year);

}
