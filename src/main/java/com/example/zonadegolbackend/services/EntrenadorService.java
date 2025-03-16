package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.dtos.CrearEquipo;
import com.example.zonadegolbackend.dtos.CrearJugador;
import com.example.zonadegolbackend.dtos.EntrenadorDTO;
import com.example.zonadegolbackend.entity.*;
import com.example.zonadegolbackend.enums.Rol;
import com.example.zonadegolbackend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;
    private final UsuarioRepository usuarioRepository;
    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;
    private final EquipoJugadorRepository equipoJugadorRepository;
    private final PasswordEncoder passwordEncoder;

    public Entrenador create(EntrenadorDTO crearEntrenador) {
        Usuario usuario = new Usuario();
        usuario.setUsername(crearEntrenador.getUsername());
        usuario.setPassword(crearEntrenador.getPassword());
        usuario.setCorreo(crearEntrenador.getCorreo());
        usuario.setRol(Rol.ENTRENADOR);

        usuarioRepository.save(usuario);

        Entrenador entrenador = new Entrenador();
        entrenador.setNombre(crearEntrenador.getNombre());
        entrenador.setApellido(crearEntrenador.getApellido());
        entrenador.setFechaNacimiento(crearEntrenador.getFechaNacimiento());
        entrenador.setDni(crearEntrenador.getDni());
        entrenador.setImagen(crearEntrenador.getImagen());
        entrenador.setUsuario(usuario);

        return entrenadorRepository.save(entrenador);
    }


    public Entrenador update(Integer idEntrenador, EntrenadorDTO crearEntrenador) {
        Entrenador entrenador = entrenadorRepository.findById(idEntrenador)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        Usuario usuario = entrenador.getUsuario();
        usuario.setUsername(crearEntrenador.getUsername());
        usuario.setPassword(crearEntrenador.getPassword());
        usuario.setCorreo(crearEntrenador.getCorreo());
        usuarioRepository.save(usuario);

        entrenador.setNombre(crearEntrenador.getNombre());
        entrenador.setApellido(crearEntrenador.getApellido());
        entrenador.setFechaNacimiento(crearEntrenador.getFechaNacimiento());
        entrenador.setDni(crearEntrenador.getDni());
        entrenador.setImagen(crearEntrenador.getImagen());
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

    public Equipo createEquipo(CrearEquipo crearEquipo) {
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

        Equipo equipo = new Equipo();
        equipo.setNombre(crearEquipo.getNombre());
        equipo.setDescripcion(crearEquipo.getDescripcion());
        equipo.setFechaFundacion(crearEquipo.getFechaFundacion());
        equipo.setImagen(crearEquipo.getImagen());
        equipo.setEntrenador(entrenador);

        return equipoRepository.save(equipo);
    }

    public Jugador createJugador(CrearJugador crearJugador) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("Username obtenido: " + username);

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

        Usuario usuarioJugador = new Usuario();
        usuarioJugador.setUsername(crearJugador.getNombre()+crearJugador.getApellido()+crearJugador.getDorsal());
        usuarioJugador.setPassword(passwordEncoder.encode(crearJugador.getDni()));
        usuarioJugador.setCorreo(crearJugador.getCorreo());
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
        jugador.setUsuario(usuarioJugador);

        jugador = jugadorRepository.save(jugador);

        EquipoJugador equipoJugador = new EquipoJugador();
        equipoJugador.setEquipo(equipo);
        equipoJugador.setJugador(jugador);

        equipoJugadorRepository.save(equipoJugador);

        return jugador;
    }







}
