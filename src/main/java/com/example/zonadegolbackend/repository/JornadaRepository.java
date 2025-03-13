package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Integer> {
}
