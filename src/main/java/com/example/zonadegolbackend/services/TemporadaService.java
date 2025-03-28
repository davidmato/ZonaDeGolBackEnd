package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Liga;
import com.example.zonadegolbackend.entity.Temporada;
import com.example.zonadegolbackend.repository.TemporadaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TemporadaService {

    private final TemporadaRepository temporadaRepository;

    public List<Temporada> findAll() {
        return temporadaRepository.findAll();
    }

    public Temporada crearTemporada(Temporada temporada) {

        Temporada nuevaTemporada = new Temporada();

        nuevaTemporada.setFechaInicio(temporada.getFechaInicio());
        nuevaTemporada.setFechaFin(temporada.getFechaFin());

        return temporadaRepository.save(nuevaTemporada);
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
}
