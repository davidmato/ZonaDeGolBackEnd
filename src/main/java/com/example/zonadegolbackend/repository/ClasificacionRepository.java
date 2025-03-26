package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Clasificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClasificacionRepository extends JpaRepository<Clasificacion, Integer> {

    List<Clasificacion> findByEquipo_Liga_IdAndTemporada_Id(Integer ligaId, Integer temporadaId);

}
