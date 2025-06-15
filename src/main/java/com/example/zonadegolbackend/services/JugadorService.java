package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.dtos.JugadorDTO;
import com.example.zonadegolbackend.entity.Estadisticas;
import com.example.zonadegolbackend.entity.Jugador;
import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.enums.Rol;
import com.example.zonadegolbackend.repository.EstadisticasRepository;
import com.example.zonadegolbackend.repository.JugadorRepository;
import com.example.zonadegolbackend.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JugadorService {

    private final JugadorRepository jugadorRepository;
    private final EstadisticasRepository estadisticasRepository;
    private final TemporadaService temporadaService;
    private final UsuarioRepository usuarioRepository;


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
        jugadorDTO.setActivo(jugador.getActivo());
        jugadorDTO.setExpulsado(jugador.getExpulsado());
        jugadorDTO.setFechaNacimiento(jugador.getFechaNacimiento());
        jugadorDTO.setCorreo(jugador.getUsuario().getCorreo());
        jugadorDTO.setEquipoNombre(jugador.getEquipo().getNombre());
        jugadorDTO.setLigaNombre(jugador.getEquipo().getLiga().getNombre());
        jugadorDTO.setEquipoFoto(jugador.getEquipo().getImagen());
        jugadorDTO.setFechaFundacion(jugador.getEquipo().getFechaFundacion());
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

    public String obtenerLigaPorJugador(Integer idJugador) {
        Jugador jugador = jugadorRepository.findById(idJugador)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        return jugador.getEquipo().getLiga().getNombre();
    }

    public Jugador editarJugador(Integer id, Jugador jugadorActualizado) {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioAutenticado.getRol()!= Rol.ADMIN && usuarioAutenticado.getRol() != Rol.ENTRENADOR) {
            throw new RuntimeException("No tienes permiso para editar jugadores");
        }


        Jugador jugadorExistente = jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        jugadorExistente.setNombre(jugadorActualizado.getNombre());
        jugadorExistente.setApellido(jugadorActualizado.getApellido());
        jugadorExistente.setDorsal(jugadorActualizado.getDorsal());
        jugadorExistente.setImagen(jugadorActualizado.getImagen());
        jugadorExistente.setFechaNacimiento(jugadorActualizado.getFechaNacimiento());
        jugadorExistente.setPosicion(jugadorActualizado.getPosicion());
        jugadorExistente.setDni(jugadorActualizado.getDni());
        jugadorExistente.setActivo(jugadorActualizado.getActivo());
        jugadorExistente.setExpulsado(jugadorActualizado.getExpulsado());

        return jugadorRepository.save(jugadorExistente);
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

    public List<JugadorDTO> findJugadoresByEntrenadorId(Integer idEntrenador) {
        List<Jugador> jugadores = jugadorRepository.findByEquipo_Entrenador_Id(idEntrenador);
        return jugadores.stream().map(jugador -> {
            JugadorDTO jugadorDTO = new JugadorDTO();
            jugadorDTO.setNombre(jugador.getNombre());
            jugadorDTO.setApellido(jugador.getApellido());
            jugadorDTO.setPosicion(jugador.getPosicion());
            jugadorDTO.setDorsal(jugador.getDorsal());
            jugadorDTO.setImagen(jugador.getImagen());
            jugadorDTO.setDni(jugador.getDni());
            jugadorDTO.setActivo(jugador.getActivo());
            jugadorDTO.setExpulsado(jugador.getExpulsado());
            jugadorDTO.setFechaNacimiento(jugador.getFechaNacimiento());
            jugadorDTO.setCorreo(jugador.getUsuario().getCorreo());
            jugadorDTO.setEquipoNombre(jugador.getEquipo().getNombre());
            jugadorDTO.setLigaNombre(jugador.getEquipo().getLiga().getNombre());
            jugadorDTO.setEquipoFoto(jugador.getEquipo().getImagen());
            jugadorDTO.setEquipoId(jugador.getEquipo().getId());
            return jugadorDTO;
        }).toList();
    }

    public List<JugadorDTO> obtenerJugadoresExpulsados() {
        List<Jugador> jugadores = jugadorRepository.findJugadorByExpulsadoTrue();

        return jugadores.stream().map(jugador -> {
            JugadorDTO dto = new JugadorDTO();
            dto.setNombre(jugador.getNombre());
            dto.setApellido(jugador.getApellido());
            dto.setPosicion(jugador.getPosicion());
            dto.setDorsal(jugador.getDorsal());
            dto.setImagen(jugador.getImagen());
            dto.setDni(jugador.getDni());
            dto.setFechaNacimiento(jugador.getFechaNacimiento());
            dto.setCorreo(jugador.getUsuario() != null ? jugador.getUsuario().getCorreo() : null);
            dto.setActivo(jugador.getActivo());
            dto.setExpulsado(jugador.getExpulsado());
            dto.setEquipoNombre(jugador.getEquipo().getNombre());
            dto.setLigaNombre(jugador.getEquipo().getLiga() != null ? jugador.getEquipo().getLiga().getNombre() : null);
            dto.setEquipoFoto(jugador.getEquipo().getImagen());
            dto.setEquipoId(jugador.getEquipo().getId());

            return dto;
        }).collect(Collectors.toList());
    }


}
