package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Jugador;
import com.example.zonadegolbackend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador,Integer> {

    Jugador findByUsuario(Usuario usuario);

    List<Jugador> findByEquipo(Equipo equipo);

    int countByEquipo(Equipo equipo);

    int countByEquipoAndActivoTrue(Equipo equipo);

    List<Jugador> findByEquipoAndExpulsadoTrue(Equipo equipo);

}
