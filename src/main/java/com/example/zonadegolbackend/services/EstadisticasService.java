package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.dtos.EstadisticaTopDTO;
import com.example.zonadegolbackend.dtos.EstadisticasDTO;
import com.example.zonadegolbackend.dtos.EstadisticasLigaTemporadaDTO;
import com.example.zonadegolbackend.entity.*;
import com.example.zonadegolbackend.enums.Rol;
import com.example.zonadegolbackend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class EstadisticasService {

    private final EstadisticasRepository estadisticasRepository;
    private final JugadorRepository jugadorRepository;
    private final EquipoRepository equipoRepository;
    private final TemporadaRepository temporadaRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Estadisticas> findAll() {
        return estadisticasRepository.findAll();
    }

    public List<EstadisticasDTO> findAllDTO() {
        List<Estadisticas> estadisticasList = estadisticasRepository.findAll();
        List<EstadisticasDTO> dtoList = new ArrayList<>();

        for (Estadisticas estadisticas : estadisticasList) {
            EstadisticasDTO dto = new EstadisticasDTO();
            dto.setPartidosJugados(estadisticas.getPartidosJugados());
            dto.setGoles(estadisticas.getGoles());
            dto.setAsistencias(estadisticas.getAsistencias());
            dto.setTarjetasAmarillas(estadisticas.getTarjetasAmarillas());
            dto.setTarjetasRojas(estadisticas.getTarjetasRojas());
            dto.setPorteriaCero(estadisticas.getPorteriaCero());
            dto.setNombreJugador(estadisticas.getJugador().getNombre());
            dto.setApellidoJugador(estadisticas.getJugador().getApellido());
            dto.setNombreLiga(estadisticas.getJugador().getEquipo().getLiga().getNombre());
            dto.setNombreTemporada(estadisticas.getTemporada().getFechaInicio() + " - " + estadisticas.getTemporada().getFechaFin());

            dtoList.add(dto);
        }

        return dtoList;
    }

    public List<Estadisticas> findByJugadorId(Integer jugadorId) {
        return estadisticasRepository.findByJugadorId(jugadorId);
    }

    public Estadisticas crearEstadisticas(Estadisticas estadisticas) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede ver los árbitros");
        }

        Estadisticas nuevaEstadisticas = new Estadisticas();

        nuevaEstadisticas.setPartidosJugados(estadisticas.getPartidosJugados());
        nuevaEstadisticas.setGoles(estadisticas.getGoles());
        nuevaEstadisticas.setAsistencias(estadisticas.getAsistencias());
        nuevaEstadisticas.setTarjetasAmarillas(estadisticas.getTarjetasAmarillas());
        nuevaEstadisticas.setTarjetasRojas(estadisticas.getTarjetasRojas());
        nuevaEstadisticas.setPorteriaCero(estadisticas.getPorteriaCero());
        nuevaEstadisticas.setTemporada(estadisticas.getTemporada());
        nuevaEstadisticas.setJugador(estadisticas.getJugador());

        return estadisticasRepository.save(estadisticas);
    }

    public Estadisticas editarEstadisticas(Integer idEstadisticas, Estadisticas estadisticas) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioAutenticado.getRol() != Rol.ARBITRO) {
            throw new RuntimeException("Solo un administrador puede ver los árbitros");
        }

        Estadisticas estadisticasExistente = estadisticasRepository.findById(idEstadisticas)
                .orElseThrow(() -> new RuntimeException("Estadisticas no encontradas"));

        estadisticasExistente.setPartidosJugados(estadisticasExistente.getPartidosJugados() + estadisticas.getPartidosJugados());
        estadisticasExistente.setGoles(estadisticasExistente.getGoles() + estadisticas.getGoles());
        estadisticasExistente.setAsistencias(estadisticasExistente.getAsistencias() + estadisticas.getAsistencias());
        estadisticasExistente.setTarjetasAmarillas(estadisticasExistente.getTarjetasAmarillas() + estadisticas.getTarjetasAmarillas());
        estadisticasExistente.setTarjetasRojas(estadisticasExistente.getTarjetasRojas() + estadisticas.getTarjetasRojas());
        estadisticasExistente.setPorteriaCero(estadisticasExistente.getPorteriaCero() + estadisticas.getPorteriaCero());

        Jugador jugador = estadisticasExistente.getJugador();
        if (estadisticas.getTarjetasRojas() > 0 || estadisticas.getTarjetasAmarillas() >= 2) {
            jugador.setExpulsado(true);
        } else {
            jugador.setExpulsado(false);
        }

        return estadisticasRepository.save(estadisticasExistente);
    }

    public Estadisticas editarEstadisticasJugador(Integer idJugador, Estadisticas estadisticas) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede ver los árbitros");
        }

        List<Estadisticas> estadisticasExistentes = estadisticasRepository.findLatestByJugadorId(idJugador);

        if (estadisticasExistentes.isEmpty()) {
            throw new RuntimeException("Estadisticas no encontradas");
        }

        Estadisticas estadisticasExistente = estadisticasExistentes.getFirst();

        estadisticasExistente.setPartidosJugados(estadisticas.getPartidosJugados());
        estadisticasExistente.setGoles(estadisticas.getGoles());
        estadisticasExistente.setAsistencias(estadisticas.getAsistencias());
        estadisticasExistente.setTarjetasAmarillas(estadisticas.getTarjetasAmarillas());
        estadisticasExistente.setTarjetasRojas(estadisticas.getTarjetasRojas());
        estadisticasExistente.setPorteriaCero(estadisticas.getPorteriaCero());

        return estadisticasRepository.save(estadisticasExistente);
    }
    public List<Estadisticas> findByTemporadaAndLiga(Integer temporadaId, Integer ligaId) {
        return estadisticasRepository.findByTemporadaAndLiga(temporadaId, ligaId);
    }

    // Hecho por DTO
    public List<EstadisticasLigaTemporadaDTO> findEstadisticasDTOByTemporadaAndLiga(Integer temporadaId, Integer ligaId) {
        return estadisticasRepository.findByTemporadaAndLigaDTO(temporadaId, ligaId);
    }

    public List<EstadisticasDTO> findEstadisticasByJugadorId(Integer jugadorId) {
        List<Estadisticas> estadisticasList = estadisticasRepository.findByJugadorId(jugadorId);
        List<EstadisticasDTO> dtoList = new ArrayList<>();

        for (Estadisticas estadisticas : estadisticasList) {
            EstadisticasDTO dto = new EstadisticasDTO();
            dto.setPartidosJugados(estadisticas.getPartidosJugados());
            dto.setGoles(estadisticas.getGoles());
            dto.setAsistencias(estadisticas.getAsistencias());
            dto.setTarjetasAmarillas(estadisticas.getTarjetasAmarillas());
            dto.setTarjetasRojas(estadisticas.getTarjetasRojas());
            dto.setPorteriaCero(estadisticas.getPorteriaCero());
            dto.setNombreJugador(estadisticas.getJugador().getNombre());
            dto.setApellidoJugador(estadisticas.getJugador().getApellido());
            dto.setNombreLiga(estadisticas.getJugador().getEquipo().getLiga().getNombre());
            dto.setNombreTemporada(estadisticas.getTemporada().getFechaInicio() + " / " + estadisticas.getTemporada().getFechaFin());
            dto.setNombreEquipo(estadisticas.getJugador().getEquipo().getNombre());

            dtoList.add(dto);
        }

        return dtoList;
    }



    public void eliminarEstadisticas(Integer id) {
        estadisticasRepository.deleteById(id);
    }


    public void evaluarExpulsion(Jugador jugador) {
        List<Estadisticas> estadisticasList = estadisticasRepository.findByJugador(jugador);

        int totalAmarillas = estadisticasList.stream()
                .mapToInt(Estadisticas::getTarjetasAmarillas)
                .sum();

        int totalRojas = estadisticasList.stream()
                .mapToInt(Estadisticas::getTarjetasRojas)
                .sum();

        int cantidadExpulsiones = (totalAmarillas / 5) + totalRojas;

        boolean estaExpulsado = cantidadExpulsiones > 0;

        jugador.setExpulsado(estaExpulsado);
        jugadorRepository.save(jugador);
    }

    public int calcularPartidosExpulsion(Jugador jugador) {
        List<Estadisticas> estadisticas = estadisticasRepository.findByJugador(jugador);
        int amarillas = estadisticas.stream().mapToInt(Estadisticas::getTarjetasAmarillas).sum();
        int rojas = estadisticas.stream().mapToInt(Estadisticas::getTarjetasRojas).sum();
        return (amarillas / 5) + rojas;
    }

    public List<Estadisticas> cargarEstadisticasPorEquipo(String nombreEquipo) {
        // Obtener el equipo por su nombre
        Equipo equipo = equipoRepository.findByNombre(nombreEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        // Obtener la última temporada creada
        Temporada ultimaTemporada = temporadaRepository.findLatest().getFirst();

        // Obtener los jugadores del equipo
        List<Jugador> jugadores = jugadorRepository.findByEquipo(equipo);

        // Cargar las estadísticas de los jugadores en la última temporada
        List<Estadisticas> estadisticasDTOs = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            Estadisticas estadisticas = estadisticasRepository.findLatestByJugadorId(jugador.getId()).getFirst();
            if (estadisticas != null) {
                Estadisticas stats = new Estadisticas();
                stats.setId(estadisticas.getId());
                stats.setPartidosJugados(estadisticas.getPartidosJugados());
                stats.setGoles(estadisticas.getGoles());
                stats.setAsistencias(estadisticas.getAsistencias());
                stats.setTarjetasAmarillas(estadisticas.getTarjetasAmarillas());
                stats.setTarjetasRojas(estadisticas.getTarjetasRojas());
                stats.setPorteriaCero(estadisticas.getPorteriaCero());
                stats.setJugador(jugador);
                stats.setTemporada(ultimaTemporada);
                estadisticasDTOs.add(stats);
            }
        }

        return estadisticasDTOs;
    }

    public List<EstadisticaTopDTO> getTopScorers() {
        Pageable topFive = PageRequest.of(0, 5);
        Page<Estadisticas> topScorers = estadisticasRepository.findTopScorers(topFive);
        return topScorers.stream()
                .map(e -> toDTO(e, e.getGoles()))
                .toList();
    }

    public List<EstadisticaTopDTO> getTopAssistants() {
        Pageable topFive = PageRequest.of(0, 5);
        Page<Estadisticas> topAssistants = estadisticasRepository.findTopAssistants(topFive);
        return topAssistants.stream()
                .map(e -> toDTO(e, e.getAsistencias()))
                .toList();
    }


    private EstadisticaTopDTO toDTO(Estadisticas e, int valor) {
        String nombre = e.getJugador().getNombre() + " " + e.getJugador().getApellido();
        String equipo = e.getJugador().getEquipo().getNombre();
        String posicion = e.getJugador().getPosicion().name();
        String imagen = e.getJugador().getImagen();
        return new EstadisticaTopDTO(nombre, equipo, posicion, imagen, valor);
    }

    public List<EstadisticaTopDTO> findTop5GoalkeepersWithMostCleanSheets() {
        Page<Estadisticas> estadisticasPage = estadisticasRepository.findTop5GoalkeepersWithMostCleanSheets(PageRequest.of(0, 5));
        List<Estadisticas> estadisticasList = estadisticasPage.getContent();

        List<EstadisticaTopDTO> dtoList = new ArrayList<>();
        for (Estadisticas estadisticas : estadisticasList) {
            String nombreCompleto = estadisticas.getJugador().getNombre() + " " + estadisticas.getJugador().getApellido();
            String equipo = estadisticas.getJugador().getEquipo().getNombre();
            String posicion = estadisticas.getJugador().getPosicion().name();
            String imagen = estadisticas.getJugador().getImagen();
            int valor = estadisticas.getPorteriaCero();

            EstadisticaTopDTO dto = new EstadisticaTopDTO(nombreCompleto, equipo, posicion, imagen, valor);
            dtoList.add(dto);
        }

        return dtoList;
    }

    public Map<String, Object> getEstadisticasEquipo(Integer equipoId) {
        Map<String, Object> estadisticas = new HashMap<>();

        Estadisticas goleador = estadisticasRepository.findTopScorerByEquipo(equipoId);
        Estadisticas asistente = estadisticasRepository.findTopAssistant(equipoId);
        Estadisticas delanteroGoleador = estadisticasRepository.findTopScoringForward(equipoId);
        Estadisticas masExpulsado = estadisticasRepository.findMostSentOffPlayer(equipoId);

        Integer porteriasDefensas = estadisticasRepository.countCleanSheetsByDefenders(equipoId);

        if (goleador != null)
            estadisticas.put("goleador", goleador.getJugador().getNombre() + " " + goleador.getJugador().getApellido());

        if (delanteroGoleador != null)
            estadisticas.put("delanteroGoleador", delanteroGoleador.getJugador().getNombre() + " " + delanteroGoleador.getJugador().getApellido());

        if (asistente != null)
            estadisticas.put("asistente", asistente.getJugador().getNombre() + " " + asistente.getJugador().getApellido());

        if (masExpulsado != null)
            estadisticas.put("expulsado", masExpulsado.getJugador().getNombre() + " " + masExpulsado.getJugador().getApellido());
        if (masExpulsado != null)
            estadisticas.put("expulsiones", masExpulsado.getTarjetasRojas());

        estadisticas.put("porteriasDefensas", porteriasDefensas != null ? porteriasDefensas : 0);

        return estadisticas;
    }


}