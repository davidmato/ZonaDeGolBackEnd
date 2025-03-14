package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.entity.Jornada;
import com.example.zonadegolbackend.repository.JornadaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JornadaService {

    private final JornadaRepository jornadaRepository;

    public List<Jornada> findAll() {
        return jornadaRepository.findAll();
    }

    public Jornada crearJornada(Jornada jornada) {

        Jornada nuevaJornada = new Jornada();

        nuevaJornada.setFecha(jornada.getFecha());
        nuevaJornada.setEquipoLocal(jornada.getEquipoLocal());
        nuevaJornada.setEquipoVisitante(jornada.getEquipoVisitante());
        nuevaJornada.setTemporada(jornada.getTemporada());

        return jornadaRepository.save(jornada);
    }

    public Jornada editarJornada(Integer idJornada, Jornada jornada) {
        Jornada jornadaExistente = jornadaRepository.findById(idJornada)
                .orElseThrow(() -> new RuntimeException("Jornada no encontrada"));

        jornadaExistente.setFecha(jornada.getFecha());
        jornadaExistente.setEquipoLocal(jornada.getEquipoLocal());
        jornadaExistente.setEquipoVisitante(jornada.getEquipoVisitante());
        jornadaExistente.setTemporada(jornada.getTemporada());

        return jornadaRepository.save(jornadaExistente);
    }

    public void eliminarJornada(Integer id) {
        jornadaRepository.deleteById(id);
    }
}
