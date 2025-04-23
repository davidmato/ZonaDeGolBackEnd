package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.dtos.JugadorDTO;
//import com.example.zonadegolbackend.entity.EquipoJugador;
import com.example.zonadegolbackend.entity.Jugador;
//import com.example.zonadegolbackend.repository.EquipoJugadorRepository;
import com.example.zonadegolbackend.repository.EquipoRepository;
import com.example.zonadegolbackend.repository.JugadorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JugadorService {

    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;
//    private final EquipoJugadorRepository equipoJugadorRepository;


    public List<Jugador> findAll() {
        return jugadorRepository.findAll();
    }

    public Jugador findById(Integer id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
    }



    public JugadorDTO findByIdDTO(Integer id) {
        Jugador jugador = jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        JugadorDTO jugadorDTO = new JugadorDTO();
        jugadorDTO.setNombre(jugador.getNombre());
        jugadorDTO.setApellido(jugador.getApellido());
        jugadorDTO.setPosicion(jugador.getPosicion());
        jugadorDTO.setDorsal(jugador.getDorsal());
        jugadorDTO.setImagen(jugador.getImagen());
        jugadorDTO.setDni(jugador.getDni());
        jugadorDTO.setFechaNacimiento(jugador.getFechaNacimiento());
        jugadorDTO.setCorreo(jugador.getUsuario().getCorreo());
        jugadorDTO.setEquipoNombre(jugador.getEquipo().getNombre());
        jugadorDTO.setLigaNombre(jugador.getEquipo().getLiga().getNombre());
        jugadorDTO.setEquipoFoto(jugador.getEquipo().getImagen());
        jugadorDTO.setEquipoId(jugador.getEquipo().getId());
        return jugadorDTO;
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

        return jugadorRepository.save(nuevoJugador);
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
