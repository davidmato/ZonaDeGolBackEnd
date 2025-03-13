package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Integer> {

}
