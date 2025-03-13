package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Entrenador;
import com.example.zonadegolbackend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador,Integer> {

    Entrenador findByUsuario(Usuario usuario);
}
