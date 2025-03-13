package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Jugador;
import com.example.zonadegolbackend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador,Integer> {

    Jugador findByUsuario(Usuario usuario);
}
