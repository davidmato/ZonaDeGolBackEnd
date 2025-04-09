package com.example.zonadegolbackend.services;


import com.example.zonadegolbackend.entity.Liga;
import com.example.zonadegolbackend.entity.Trofeo;
import com.example.zonadegolbackend.repository.LigaRepository;
import com.example.zonadegolbackend.repository.TrofeoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LigaService {

    private final LigaRepository ligaRepository;
    private final TrofeoRepository trofeoRepository;

    public List<Liga> findAll() {
        return ligaRepository.findAll();
    }

//    @Transactional
//    public Liga crearLiga(Liga liga) {
//
//        Trofeo trofeo = liga.getTrofeo();
//        if (trofeo != null) {
//            trofeoRepository.save(trofeo);
//        }
//        return ligaRepository.save(liga);
//    }

    public Liga crearLiga(Liga liga){

        Liga nuevaLiga = new Liga();

        nuevaLiga.setNombre(liga.getNombre());
        nuevaLiga.setNumEquipos(liga.getNumEquipos());
        nuevaLiga.setDescripcion(liga.getDescripcion());
        nuevaLiga.setFecha_fundacion(liga.getFecha_fundacion());

        return ligaRepository.save(nuevaLiga);
    }

    public Liga editarLiga(Integer id, Liga liga){
        Liga ligaExistente = ligaRepository.findById(id).orElseThrow(() -> new RuntimeException("Liga no encontrada"));
        ligaExistente.setNombre(liga.getNombre());
        ligaExistente.setNumEquipos(liga.getNumEquipos());
        ligaExistente.setDescripcion(liga.getDescripcion());
        ligaExistente.setFecha_fundacion(liga.getFecha_fundacion());

        return ligaRepository.save(ligaExistente);
    }

    public void eliminarLiga(Integer id){
        Liga ligaExistente = ligaRepository.findById(id).orElseThrow(() -> new RuntimeException("Liga no encontrada"));

        ligaRepository.delete(ligaExistente);
    }


}
