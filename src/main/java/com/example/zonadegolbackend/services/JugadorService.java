package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.entity.Jugador;
import com.example.zonadegolbackend.repository.JugadorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    public List<Jugador> findAll() {
        return jugadorRepository.findAll();
    }

    public Jugador crearJugador (Jugador jugador) {

        Jugador nuevoJugador = new Jugador();

        nuevoJugador.setNombre(jugador.getNombre());
        nuevoJugador.setApellido(jugador.getApellido());
        nuevoJugador.setDorsal(jugador.getDorsal());
        nuevoJugador.setImagen(jugador.getImagen());
        nuevoJugador.setFechaNacimiento(jugador.getFechaNacimiento());
        nuevoJugador.setPosicion(jugador.getPosicion());
        nuevoJugador.setDni(jugador.getDni());
        nuevoJugador.setUsuario(jugador.getUsuario());

        return jugadorRepository.save(nuevoJugador);
    }
}
