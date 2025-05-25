package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Estadio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadioRepository extends JpaRepository<Estadio, Integer> {
}

