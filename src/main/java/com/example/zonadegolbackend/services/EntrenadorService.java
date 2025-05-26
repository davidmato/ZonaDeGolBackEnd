package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.dtos.EntrenadorDTO;
import com.example.zonadegolbackend.dtos.CrearEquipo;
import com.example.zonadegolbackend.dtos.CrearJugador;
import com.example.zonadegolbackend.entity.*;
import com.example.zonadegolbackend.enums.Rol;
import com.example.zonadegolbackend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;
    private final UsuarioRepository usuarioRepository;
    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;
    private final LigaRepository ligaRepository;
    private final PasswordEncoder passwordEncoder;
    private final EstadisticasRepository estadisticasRepository;
    private final TemporadaService temporadaService;


    public List<EntrenadorDTO> listarEntrenador() {
        List<Entrenador> entrenadores = entrenadorRepository.findAll();
        List<EntrenadorDTO> entrenadorDTOS = new ArrayList<>();

        for (Entrenador entrenador : entrenadores) {
            EntrenadorDTO entrenadorDTO = new EntrenadorDTO();
            entrenadorDTO.setNombre(entrenador.getNombre());
            entrenadorDTO.setApellido(entrenador.getApellido());
            entrenadorDTO.setFechaNacimiento(entrenador.getFechaNacimiento());
            entrenadorDTO.setDni(entrenador.getDni());
            entrenadorDTO.setImagen(entrenador.getImagen());
            entrenadorDTO.setUsername(entrenador.getUsuario().getUsername());
            entrenadorDTO.setCorreo(entrenador.getUsuario().getCorreo());
            entrenadorDTOS.add(entrenadorDTO);
        }
        return entrenadorDTOS;
    }

    public List<Entrenador> findAll() {
        return entrenadorRepository.findAll();
    }


    public Entrenador create(EntrenadorDTO entrenadorDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsername(entrenadorDTO.getUsername());
        usuario.setPassword(entrenadorDTO.getPassword());
        usuario.setCorreo(entrenadorDTO.getCorreo());
        usuario.setPagado(true);
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setRol(Rol.ENTRENADOR);

        usuarioRepository.save(usuario);

        Entrenador entrenador = new Entrenador();
        entrenador.setNombre(entrenadorDTO.getNombre());
        entrenador.setApellido(entrenadorDTO.getApellido());
        entrenador.setFechaNacimiento(entrenadorDTO.getFechaNacimiento());
        entrenador.setDni(entrenadorDTO.getDni());
        entrenador.setImagen(entrenadorDTO.getImagen());
        entrenador.setUsuario(usuario);

        return entrenadorRepository.save(entrenador);
    }


    public Entrenador update(Integer idEntrenador, EntrenadorDTO entrenadorDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede editar entrenadores");
        }

        Entrenador entrenador = entrenadorRepository.findById(idEntrenador)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        Usuario usuario = entrenador.getUsuario();
        usuario.setUsername(entrenadorDTO.getUsername());
        usuario.setPassword(entrenadorDTO.getPassword());
        usuario.setCorreo(entrenadorDTO.getCorreo());
        usuarioRepository.save(usuario);

        entrenador.setNombre(entrenadorDTO.getNombre());
        entrenador.setApellido(entrenadorDTO.getApellido());
        entrenador.setFechaNacimiento(entrenadorDTO.getFechaNacimiento());
        entrenador.setDni(entrenadorDTO.getDni());
        entrenador.setImagen(entrenadorDTO.getImagen());
        entrenador.setUsuario(usuario);

        return entrenadorRepository.save(entrenador);
    }

    public void delete(Integer idEntrenador) {
        Entrenador entrenador = entrenadorRepository.findById(idEntrenador)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));
        entrenadorRepository.delete(entrenador);
    }

    public void deleteEyU(Integer idEntrenador) {
        Entrenador entrenador = entrenadorRepository.findById(idEntrenador)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));
        Usuario usuario = entrenador.getUsuario();
        entrenadorRepository.delete(entrenador);
        usuarioRepository.delete(usuario);
    }

    public Equipo createEquipo(CrearEquipo crearEquipo, Integer idLiga) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.ENTRENADOR) {
            throw new RuntimeException("Solo un entrenador puede crear un equipo");
        }

        Entrenador entrenador = entrenadorRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        if (equipoRepository.existsByEntrenador(entrenador)) {
            throw new RuntimeException("El entrenador ya tiene un equipo");
        }

        Liga liga = ligaRepository.findById(idLiga)
                .orElseThrow(() -> new RuntimeException("Liga no encontrada"));

        Equipo equipo = new Equipo();
        equipo.setNombre(crearEquipo.getNombre());
        equipo.setDescripcion(crearEquipo.getDescripcion());
        equipo.setFechaFundacion(crearEquipo.getFechaFundacion());
        equipo.setImagen(crearEquipo.getImagen());
        equipo.setEntrenador(entrenador);
        equipo.setLiga(liga);

        return equipoRepository.save(equipo);
    }

    public Jugador createJugador(CrearJugador crearJugador) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.ENTRENADOR) {
            throw new RuntimeException("Solo un entrenador puede crear un jugador");
        }

        Entrenador entrenador = entrenadorRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        Equipo equipo = equipoRepository.findByEntrenador(entrenador);
        if (equipo == null) {
            throw new RuntimeException("El entrenador no tiene un equipo");
        }

        int cantidadJugadores = jugadorRepository.countByEquipoAndActivoTrue(equipo);
        if (cantidadJugadores >= 12) {
            throw new RuntimeException("No se pueden crear más de 12 jugadores por equipo");
        }

        Usuario usuarioJugador = new Usuario();
        usuarioJugador.setUsername(crearJugador.getNombre()+crearJugador.getApellido()+crearJugador.getDorsal());
        usuarioJugador.setPassword(passwordEncoder.encode(crearJugador.getDni()));
        usuarioJugador.setCorreo(crearJugador.getCorreo());
        usuarioJugador.setPagado(true);
        usuarioJugador.setFechaRegistro(LocalDateTime.now());
        usuarioJugador.setRol(Rol.JUGADOR);

        usuarioRepository.save(usuarioJugador);

        Jugador jugador = new Jugador();
        jugador.setNombre(crearJugador.getNombre());
        jugador.setApellido(crearJugador.getApellido());
        jugador.setPosicion(crearJugador.getPosicion());
        jugador.setDorsal(crearJugador.getDorsal());
        jugador.setFechaNacimiento(crearJugador.getFechaNacimiento());
        jugador.setImagen(crearJugador.getImagen());
        jugador.setDni(crearJugador.getDni());
        jugador.setExpulsado(false);
        jugador.setActivo(true);
        jugador.setUsuario(usuarioJugador);
        jugador.setEquipo(equipo);

        jugador = jugadorRepository.save(jugador);

        Estadisticas estadisticas = new Estadisticas();
        estadisticas.setGoles(0);
        estadisticas.setAsistencias(0);
        estadisticas.setTarjetasAmarillas(0);
        estadisticas.setTarjetasRojas(0);
        estadisticas.setPartidosJugados(0);
        estadisticas.setPorteriaCero(0);
        estadisticas.setTemporada(temporadaService.buscarTemporadaPorAnioActual());
        estadisticas.setJugador(jugador);
        estadisticasRepository.save(estadisticas);


        return jugador;
    }

    public Equipo updateEquipo(CrearEquipo crearEquipo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.ENTRENADOR) {
            throw new RuntimeException("Solo un entrenador puede editar un equipo");
        }

        Entrenador entrenador = entrenadorRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        Equipo equipo = equipoRepository.findByEntrenador(entrenador);
        if (equipo == null) {
            throw new RuntimeException("El entrenador no tiene un equipo asignado");
        }

        equipo.setNombre(crearEquipo.getNombre());
        equipo.setDescripcion(crearEquipo.getDescripcion());
        equipo.setFechaFundacion(crearEquipo.getFechaFundacion());
        equipo.setImagen(crearEquipo.getImagen());

        return equipoRepository.save(equipo);
    }

    public Jugador updateJugador(CrearJugador crearJugador) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.ENTRENADOR) {
            throw new RuntimeException("Solo un entrenador puede editar un jugador");
        }

        Entrenador entrenador = entrenadorRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        Equipo equipo = equipoRepository.findByEntrenador(entrenador);
        if (equipo == null) {
            throw new RuntimeException("El entrenador no tiene un equipo asignado");
        }

        Jugador jugador = jugadorRepository.findById(crearJugador.getId())
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        jugador.setNombre(crearJugador.getNombre());
        jugador.setApellido(crearJugador.getApellido());
        jugador.setPosicion(crearJugador.getPosicion());
        jugador.setDorsal(crearJugador.getDorsal());
        jugador.setFechaNacimiento(crearJugador.getFechaNacimiento());
        jugador.setImagen(crearJugador.getImagen());
        jugador.setDni(crearJugador.getDni());

        return jugadorRepository.save(jugador);
    }

    public Jugador cambiarEstadoActivoJugador(Integer idJugador) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.ENTRENADOR) {
            throw new RuntimeException("Solo un entrenador puede cambiar el estado de un jugador");
        }

        Entrenador entrenador = entrenadorRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        Equipo equipo = equipoRepository.findByEntrenador(entrenador);
        if (equipo == null) {
            throw new RuntimeException("El entrenador no tiene un equipo asignado");
        }

        Jugador jugador = jugadorRepository.findById(idJugador)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        if (!jugador.getEquipo().getId().equals(equipo.getId())) {
            throw new RuntimeException("El jugador no pertenece a tu equipo");
        }

        jugador.setActivo(!jugador.isActivo());
        return jugadorRepository.save(jugador);
    }










}
