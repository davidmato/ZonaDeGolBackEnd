package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.entity.Equipo;
//import com.example.zonadegolbackend.entity.EquipoLiga;
import com.example.zonadegolbackend.entity.Jornada;
import com.example.zonadegolbackend.entity.Temporada;
import com.example.zonadegolbackend.repository.EquipoRepository;
import com.example.zonadegolbackend.repository.JornadaRepository;
//import com.example.zonadegolbackend.repository.LigaEquipoRepository;
import com.example.zonadegolbackend.repository.TemporadaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JornadaService {

    private final JornadaRepository jornadaRepository;
    private final EquipoRepository equipoRepository;
    private final TemporadaRepository temporadaRepository;
//    private final LigaEquipoRepository ligaEquipoRepository;

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

//    public List<Jornada> generarJornadas(Temporada temporada) {
//        List<EquipoLiga> equiposLiga = ligaEquipoRepository.findByTemporada(temporada);
//        List<Equipo> equipos = new ArrayList<Equipo>();
//        for (EquipoLiga equipoLiga : equiposLiga) {
//            equipos.add(equipoLiga.getEquipo());
//        }
//        return generarJornadas(equipos, temporada);
//    }

    public List<Jornada> generarJornadas(List<Integer> equipoIds, Integer temporadaId) {
        List<Equipo> equipos = equipoRepository.findAllById(equipoIds);
        Temporada temporada = temporadaRepository.findById(temporadaId)
                .orElseThrow(() -> new IllegalArgumentException("Temporada no encontrada"));

        List<Jornada> jornadas = new ArrayList<>();
        Random random = new Random();

        if (equipos.size() < 2) {
            throw new IllegalArgumentException("Debe haber al menos dos equipos para generar jornadas.");
        }

        Map<String, Integer> enfrentamientos = new HashMap<>();
        List<List<Equipo>> enfrentamientosPendientes = new ArrayList<>();

        for (int i = 0; i < equipos.size(); i++) {
            for (int j = i + 1; j < equipos.size(); j++) {
                enfrentamientosPendientes.add(Arrays.asList(equipos.get(i), equipos.get(j)));
            }
        }

        while (!enfrentamientosPendientes.isEmpty()) {
            List<Equipo> par = enfrentamientosPendientes.remove(random.nextInt(enfrentamientosPendientes.size()));
            Equipo equipoLocal = par.get(0);
            Equipo equipoVisitante = par.get(1);

            String claveEnfrentamiento = equipoLocal.getId() + "-" + equipoVisitante.getId();
            enfrentamientos.putIfAbsent(claveEnfrentamiento, 0);

            if (enfrentamientos.get(claveEnfrentamiento) < 2) {
                Jornada jornadaIda = new Jornada();
                jornadaIda.setFecha(LocalDateTime.now().plusDays(jornadas.size()));
                jornadaIda.setEquipoLocal(equipoLocal);
                jornadaIda.setEquipoVisitante(equipoVisitante);
                jornadaIda.setTemporada(temporada);
                jornadaIda.setGolLocal(0);
                jornadaIda.setGolVisitante(0);
                jornadas.add(jornadaIda);
                enfrentamientos.put(claveEnfrentamiento, enfrentamientos.get(claveEnfrentamiento) + 1);

                Jornada jornadaVuelta = new Jornada();
                jornadaVuelta.setFecha(LocalDateTime.now().plusDays(jornadas.size()));
                jornadaVuelta.setEquipoLocal(equipoVisitante);
                jornadaVuelta.setEquipoVisitante(equipoLocal);
                jornadaVuelta.setTemporada(temporada);
                jornadaVuelta.setGolLocal(0);
                jornadaVuelta.setGolVisitante(0);
                jornadas.add(jornadaVuelta);
                enfrentamientos.put(claveEnfrentamiento, enfrentamientos.get(claveEnfrentamiento) + 1);
            }
        }

        jornadaRepository.saveAll(jornadas);
        return jornadas;
    }

//    public void actualizarPuntos (Jornada jornada) {
//
//        Equipo equipoLocal = jornada.getEquipoLocal();
//        Equipo equipoVisitante = jornada.getEquipoVisitante();
//
//        int golesLocal = jornada.getGolLocal();
//        int golesVisitante = jornada.getGolVisitante();
//
//        if(golesLocal > golesVisitante) {
//            equipoLocal.setPuntos
//        }
//    }

}
