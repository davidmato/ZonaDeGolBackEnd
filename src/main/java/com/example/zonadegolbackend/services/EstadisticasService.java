package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.dtos.CrearEquipo;
import com.example.zonadegolbackend.entity.Entrenador;
import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Estadisticas;
import com.example.zonadegolbackend.entity.Jugador;
import com.example.zonadegolbackend.repository.EstadisticasRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EstadisticasService {

    private final EstadisticasRepository estadisticasRepository;

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

    public void eliminarEstadisticas(Integer id) {
        estadisticasRepository.deleteById(id);
    }
}