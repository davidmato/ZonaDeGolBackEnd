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

    public List<Temporada> findAll() {
        return temporadaRepository.findAll();
    }

    public Temporada crearTemporada(Temporada temporada) {

        Temporada nuevaTemporada = new Temporada();

        nuevaTemporada.setFechaInicio(temporada.getFechaInicio());
        nuevaTemporada.setFechaFin(temporada.getFechaFin());
        nuevaTemporada.setLiga(temporada.getLiga());
        temporadaRepository.save(nuevaTemporada);
        crearClasificacionesEquipo(nuevaTemporada);
        crearEstadisticasJugador(nuevaTemporada);

        return nuevaTemporada;
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
            clasificacion.setVictorias(0);
            clasificacion.setEmpates(0);
            clasificacion.setDerrotas(0);
            clasificacion.setGolAFavor(0);
            clasificacion.setGolEnContra(0);
            clasificacion.setGolDiferencia(0);
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
            estadisticas.setTemporada(temporada);
            estadisticas.setJugador(jugador);
            estadisticasRepository.save(estadisticas);
        }
    }
}
