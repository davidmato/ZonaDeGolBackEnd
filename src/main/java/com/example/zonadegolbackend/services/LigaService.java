package com.example.zonadegolbackend.services;


import com.example.zonadegolbackend.entity.Liga;
import com.example.zonadegolbackend.repository.LigaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LigaService {

    private final LigaRepository ligaRepository;

    public List<Liga> findAll() {
        return ligaRepository.findAll();
    }

    public void crearLiga(Liga liga) {

        Liga nuevaLiga = new Liga();

        nuevaLiga.setNombre(liga.getNombre());
        nuevaLiga.setNumEquipos(liga.getNumEquipos());
        nuevaLiga.setDescripcion(liga.getDescripcion());
        nuevaLiga.setFecha_fundacion(liga.getFecha_fundacion());
        nuevaLiga.setTrofeos(liga.getTrofeos());

        ligaRepository.save(nuevaLiga);
    }
}
