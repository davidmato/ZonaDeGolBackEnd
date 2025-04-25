package com.example.zonadegolbackend.services;


import com.example.zonadegolbackend.entity.*;
import com.example.zonadegolbackend.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LigaService {

    private final LigaRepository ligaRepository;
    private final TrofeoRepository trofeoRepository;
    private final EquipoRepository equipoRepository;
    private final TemporadaRepository temporadaRepository;
    private final ClasificacionRepository clasificacionRepository;
    private final ClasificacionService clasificacionService;

    public List<Liga> findAll() {
        return ligaRepository.findAll();
    }

    public Liga findById(Integer id) {
        return ligaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Liga no encontrada"));
    }

    public List<Equipo> findByLigaId(Integer ligaId) {
        return equipoRepository.findByLigaId(ligaId);
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

    public List<Clasificacion> obtenerClasificacionLigaTemporadaReciente(Integer ligaId) {
        Temporada temporadaReciente = temporadaRepository.findLatest().getFirst();
        clasificacionService.actualizarPuestosYObtenerClasificacion(ligaId, temporadaReciente.getId());
        return clasificacionRepository.findByEquipo_Liga_IdAndTemporada_Id(ligaId, temporadaReciente.getId());
    }

}
