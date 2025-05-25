package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.dtos.EntrenadorDTO;
import com.example.zonadegolbackend.dtos.EstadisticasDTO;
import com.example.zonadegolbackend.dtos.EstadisticasLigaTemporadaDTO;
import com.example.zonadegolbackend.entity.Estadisticas;
import com.example.zonadegolbackend.entity.Jugador;
import com.example.zonadegolbackend.repository.JugadorRepository;
import com.example.zonadegolbackend.services.EstadisticasService;
import com.example.zonadegolbackend.services.JugadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    private final EstadisticasService estadisticasService;
    private final JugadorRepository jugadorRepository;

    @GetMapping("/listar")
    public List<Estadisticas> findAll() {
        return estadisticasService.findAll();
    }

    @GetMapping("/listar/dto")
    public List<EstadisticasDTO> findAllDTO() {
        return estadisticasService.findAllDTO();
    }

    @GetMapping("/jugador/listar/{jugadorId}")
    public List<Estadisticas> findByJugadorId(@PathVariable Integer jugadorId) {
        return estadisticasService.findByJugadorId(jugadorId);
    }

    @PostMapping("/crear")
    public Estadisticas crearEstadisticas(@RequestBody Estadisticas estadisticas) {
        return estadisticasService.crearEstadisticas(estadisticas);
    }

    @PutMapping("/editar/{id}")
    public Estadisticas editarEstadisticas(@PathVariable Integer id, @RequestBody Estadisticas estadisticas) {
        return estadisticasService.editarEstadisticas(id, estadisticas);
    }

    @PutMapping("/jugador/editar/{jugadorId}")
    public Estadisticas editarEstadisticasJugador(@PathVariable Integer jugadorId, @RequestBody Estadisticas estadisticas) {
        return estadisticasService.editarEstadisticasJugador(jugadorId, estadisticas);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarEstadisticas(@PathVariable Integer id) {
        estadisticasService.eliminarEstadisticas(id);
    }


    @GetMapping("/temporada/{temporadaId}/liga/{ligaId}")
    public List<Estadisticas> getEstadisticasByTemporadaAndLiga(
            @PathVariable Integer temporadaId,
            @PathVariable Integer ligaId) {
        return estadisticasService.findByTemporadaAndLiga(temporadaId, ligaId);
    }


    @GetMapping("/temporada/{temporadaId}/{ligaId}")
    public List<EstadisticasLigaTemporadaDTO> getEstadisticasByTemporadaAndLigaDTO(
            @PathVariable Integer temporadaId,
            @PathVariable Integer ligaId) {
        return estadisticasService.findEstadisticasDTOByTemporadaAndLiga(temporadaId, ligaId);
    }

    @GetMapping("/jugador/{id}")
    public List<EstadisticasDTO> getEstadisticasByJugadorId(@PathVariable Integer id) {
       return estadisticasService.findEstadisticasByJugadorId(id);
    }

    @PostMapping("/{id}/evaluar-expulsion")
    public String evaluarExpulsion(@PathVariable Integer id) {
        Jugador jugador = jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        estadisticasService.evaluarExpulsion(jugador);

        return "Expulsión evaluada. Estado actual: expulsado = " + jugador.getExpulsado();
    }

    @GetMapping("/{id}/partidos-expulsion")
    public int calcularPartidosExpulsion(@PathVariable Integer id) {
        Jugador jugador = jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        return estadisticasService.calcularPartidosExpulsion(jugador);
    }

}
