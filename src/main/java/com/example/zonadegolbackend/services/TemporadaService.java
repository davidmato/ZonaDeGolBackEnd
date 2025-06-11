package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.entity.*;
import com.example.zonadegolbackend.repository.*;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TemporadaService {

    private final TemporadaRepository temporadaRepository;
    private final ClasificacionRepository clasificacionRepository;
    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;
    private final EstadisticasRepository estadisticasRepository;
    private final LigaRepository ligaRepository;
    private final TemporadaLigaRepository temporadaLigaRepository;
    private final TrofeoRepository trofeoRepository;

    public List<Temporada> findAll() {
        return temporadaRepository.findAll();
    }

    public Temporada crearTemporada(Temporada temporada) {

        Temporada nuevaTemporada = new Temporada();

        nuevaTemporada.setFechaInicio(temporada.getFechaInicio());
        nuevaTemporada.setFechaFin(temporada.getFechaFin());
        temporadaRepository.save(nuevaTemporada);
        crearClasificacionesEquipo(nuevaTemporada);
        crearEstadisticasJugador(nuevaTemporada);
        crearTemporadaLiga(nuevaTemporada);

        return nuevaTemporada;
    }

    public Temporada editarTemporada(Integer id, Temporada temporada) {
        Temporada temporadaExistente = temporadaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Temporada no encontrada"));

        temporadaExistente.setFechaInicio(temporada.getFechaInicio());
        temporadaExistente.setFechaFin(temporada.getFechaFin());

        return temporadaRepository.save(temporadaExistente);
    }

    public void eliminarTemporada(Integer id) {
        temporadaRepository.deleteById(id);
    }

    public Temporada buscarTemporadaPorAnioActual() {
        int currentYear = LocalDate.now().getYear();
        return temporadaRepository.findByYear(currentYear);
    }

    public Temporada buscarTemporadaMasReciente() {
        return temporadaRepository.findLatest().getFirst();
    }

    public void crearClasificacionesEquipo(Temporada temporada){
        List<Equipo> equipos = equipoRepository.findAll();
        for (Equipo equipo : equipos) {
            Clasificacion clasificacion = new Clasificacion();
            clasificacion.setPuesto(0);
            clasificacion.setPartidosJugados(0);
            clasificacion.setVictorias(0);
            clasificacion.setEmpates(0);
            clasificacion.setDerrotas(0);
            clasificacion.setGolAFavor(0);
            clasificacion.setGolEnContra(0);
            clasificacion.setGolDiferencia(0);
            clasificacion.setPartidosJugados(0);
            clasificacion.setPuntos(0);
            clasificacion.setEquipo(equipo);
            clasificacion.setTemporada(temporada);
            clasificacionRepository.save(clasificacion);
        }
    }

    public void crearEstadisticasJugador(Temporada temporada){
        List<Jugador> jugadores = jugadorRepository.findAll();
        for (Jugador jugador : jugadores) {
            Estadisticas estadisticas = new Estadisticas();
            estadisticas.setGoles(0);
            estadisticas.setAsistencias(0);
            estadisticas.setTarjetasAmarillas(0);
            estadisticas.setTarjetasRojas(0);
            estadisticas.setPartidosJugados(0);
            estadisticas.setPorteriaCero(0);
            estadisticas.setPartidosJugados(0);
            estadisticas.setTemporada(temporada);
            estadisticas.setJugador(jugador);
            estadisticasRepository.save(estadisticas);
        }
    }

    public void crearTemporadaLiga(Temporada temporada) {
        List<Liga> ligas = ligaRepository.findAll();
        for (Liga liga : ligas) {
            TemporadaLiga temporadaLiga = new TemporadaLiga();
            temporadaLiga.setTemporada(temporada);
            temporadaLiga.setLiga(liga);
            temporadaLigaRepository.save(temporadaLiga);
            Trofeo trofeo = new Trofeo();
            trofeo.setNombre("Trofeo de " + liga.getNombre() + " " + temporada.getFechaInicio().getYear());
            trofeo.setImagen("icons8-trofeo-de-la-eurocopa-de-la-uefa-70.png"); // Puedes cambiar esto por una imagen real si la tienes
            trofeo.setTemporadaLiga(temporadaLiga);
            trofeoRepository.save(trofeo);
        }
    }

    public TemporadaLiga buscarTemporadaLiga(Integer temporadaId, Integer ligaId) {
        return temporadaLigaRepository.findByTemporadaIdAndLigaId(temporadaId, ligaId);
    }
}
