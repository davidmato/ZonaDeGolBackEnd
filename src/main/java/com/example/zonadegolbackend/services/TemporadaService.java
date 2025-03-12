package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.entity.Temporada;
import com.example.zonadegolbackend.repository.TemporadaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TemporadaService {

    private final TemporadaRepository temporadaRepository;

    public List<Temporada> findAll() {
        return temporadaRepository.findAll();
    }

    public void crearTemporada(Temporada temporada) {

        Temporada nuevaTemporada = new Temporada();

        nuevaTemporada.setFechaInicio(temporada.getFechaInicio());
        nuevaTemporada.setFechaFin(temporada.getFechaFin());
        nuevaTemporada.setLiga(temporada.getLiga());

        temporadaRepository.save(nuevaTemporada);
    }
}
