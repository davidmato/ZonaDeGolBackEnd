package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.dtos.EstadisticaTopDTO;
import com.example.zonadegolbackend.dtos.EstadisticasDTO;
import com.example.zonadegolbackend.dtos.EstadisticasLigaTemporadaDTO;
import com.example.zonadegolbackend.entity.Estadisticas;
import com.example.zonadegolbackend.entity.Jugador;
import com.example.zonadegolbackend.repository.EstadisticasRepository;
import com.example.zonadegolbackend.repository.JugadorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EstadisticasService {

    private final EstadisticasRepository estadisticasRepository;
    private final JugadorRepository jugadorRepository;

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
        Estadisticas estadisticasExistente = estadisticasRepository.findById(idEstadisticas)
                .orElseThrow(() -> new RuntimeException("Estadisticas no encontradas"));

        estadisticasExistente.setPartidosJugados(estadisticas.getPartidosJugados());
        estadisticasExistente.setGoles(estadisticas.getGoles());
        estadisticasExistente.setAsistencias(estadisticas.getAsistencias());
        estadisticasExistente.setTarjetasAmarillas(estadisticas.getTarjetasAmarillas());
        estadisticasExistente.setTarjetasRojas(estadisticas.getTarjetasRojas());
        estadisticasExistente.setPorteriaCero(estadisticas.getPorteriaCero());
        estadisticasExistente.setTemporada(estadisticas.getTemporada());
        estadisticasExistente.setJugador(estadisticas.getJugador());

        return estadisticasRepository.save(estadisticasExistente);
    }

    public Estadisticas editarEstadisticasJugador(Integer idJugador, Estadisticas estadisticas) {
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

}