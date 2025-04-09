package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.entity.Equipo;
//import com.example.zonadegolbackend.entity.EquipoJugador;
import com.example.zonadegolbackend.entity.Estadisticas;
import com.example.zonadegolbackend.entity.Jugador;
//import com.example.zonadegolbackend.repository.EquipoJugadorRepository;
import com.example.zonadegolbackend.entity.Temporada;
import com.example.zonadegolbackend.repository.EquipoRepository;
import com.example.zonadegolbackend.repository.EstadisticasRepository;
import com.example.zonadegolbackend.repository.JugadorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class JugadorService {

    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;
//    private final EquipoJugadorRepository equipoJugadorRepository;
    private final EstadisticasRepository estadisticasRepository;
    private final TemporadaService temporadaService;


    public List<Jugador> findAll() {
        return jugadorRepository.findAll();
    }

    public Jugador findById(Integer id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
    }

    public Jugador crearJugador(Jugador jugador) {

        Jugador nuevoJugador = new Jugador();

        nuevoJugador.setNombre(jugador.getNombre());
        nuevoJugador.setApellido(jugador.getApellido());
        nuevoJugador.setDorsal(jugador.getDorsal());
        nuevoJugador.setImagen(jugador.getImagen());
        nuevoJugador.setFechaNacimiento(jugador.getFechaNacimiento());
        nuevoJugador.setPosicion(jugador.getPosicion());
        nuevoJugador.setDni(jugador.getDni());
        nuevoJugador.setUsuario(jugador.getUsuario());
        jugadorRepository.save(nuevoJugador);

        Estadisticas estadisticas = new Estadisticas();
        estadisticas.setGoles(0);
        estadisticas.setAsistencias(0);
        estadisticas.setTarjetasAmarillas(0);
        estadisticas.setTarjetasRojas(0);
        estadisticas.setPartidosJugados(0);
        estadisticas.setPorteriaCero(0);
        estadisticas.setTemporada(temporadaService.buscarTemporadaPorAnioActual());
        estadisticas.setJugador(nuevoJugador);
        estadisticasRepository.save(estadisticas);

        return nuevoJugador;
    }

//
//    @Transactional
//    public void associatePlayersWithTeam(Integer idEquipo, List<Integer> idJugadores) {
//        Equipo equipo = equipoRepository.findById(idEquipo)
//                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
//
//        for (Integer idJugador : idJugadores) {
//            Jugador jugador = jugadorRepository.findById(idJugador)
//                    .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
//
//            EquipoJugador equipoJugador = new EquipoJugador();
//            equipoJugador.setEquipo(equipo);
//            equipoJugador.setJugador(jugador);
//            equipoJugadorRepository.save(equipoJugador);
//        }
//    }
}
