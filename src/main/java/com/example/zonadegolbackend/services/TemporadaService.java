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
        nuevaTemporada.setLiga(temporada.getLiga());

        return temporadaRepository.save(nuevaTemporada);
    }

    public Temporada buscarTemporadaPorAnioActual() {
        int currentYear = LocalDate.now().getYear();
        return temporadaRepository.findByYear(currentYear);
    }
}
