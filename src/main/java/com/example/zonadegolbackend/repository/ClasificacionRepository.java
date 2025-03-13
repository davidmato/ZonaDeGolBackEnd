package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Clasificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClasificacionRepository extends JpaRepository<Clasificacion, Integer> {
}
