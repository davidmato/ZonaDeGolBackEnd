package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.entity.Clasificacion;
import com.example.zonadegolbackend.entity.Equipo;
//import com.example.zonadegolbackend.entity.EquipoLiga;
import com.example.zonadegolbackend.entity.Jornada;
import com.example.zonadegolbackend.entity.Temporada;
import com.example.zonadegolbackend.entity.Arbitro;
import com.example.zonadegolbackend.entity.Estadio;
import com.example.zonadegolbackend.dtos.JornadaDTO;
import com.example.zonadegolbackend.repository.*;
//import com.example.zonadegolbackend.repository.LigaEquipoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    private final ClasificacionRepository clasificacionRepository;
    private final ClasificacionService clasificacionService;
    private final ArbitroRepository arbitroRepository;
    private final EstadioRepository estadioRepository;

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

    private JornadaDTO mapToDTO(Jornada jornada) {
        return new JornadaDTO(
                jornada.getId(),
                jornada.getFecha(),
                jornada.getGolLocal(),
                jornada.getGolVisitante(),
                jornada.getEquipoLocal().getNombre(),
                jornada.getEquipoVisitante().getNombre(),
                jornada.getArbitro() != null ? jornada.getArbitro().getNombre() + " " + jornada.getArbitro().getApellidos() : null,
                jornada.getEstadio() != null ? jornada.getEstadio().getNombre() : null
        );
    }


    public List<JornadaDTO> generarJornadas(List<Integer> equipoIds, Integer temporadaId) {
        List<Equipo> equipos = equipoRepository.findAllById(equipoIds);
        Temporada temporada = temporadaRepository.findById(temporadaId)
                .orElseThrow(() -> new IllegalArgumentException("Temporada no encontrada"));

        List<Arbitro> arbitros = arbitroRepository.findAll();
        List<Estadio> estadios = estadioRepository.findAll();
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

            String clave = equipoLocal.getId() + "-" + equipoVisitante.getId();
            enfrentamientos.putIfAbsent(clave, 0);

            if (enfrentamientos.get(clave) < 2) {
                for (int i = 0; i < 2; i++) {
                    Jornada jornada = new Jornada();
                    jornada.setFecha(LocalDateTime.now().plusDays(jornadas.size()));
                    jornada.setEquipoLocal(i == 0 ? equipoLocal : equipoVisitante);
                    jornada.setEquipoVisitante(i == 0 ? equipoVisitante : equipoLocal);
                    jornada.setTemporada(temporada);
                    jornada.setGolLocal(0);
                    jornada.setGolVisitante(0);

                    jornada.setArbitro(arbitros.get(random.nextInt(arbitros.size())));
                    jornada.setEstadio(estadios.get(random.nextInt(estadios.size())));

                    jornadas.add(jornada);
                    enfrentamientos.put(clave, enfrentamientos.get(clave) + 1);
                }
            }
        }

        jornadaRepository.saveAll(jornadas);

        return jornadas.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }



    public void actualizarPuntos (Jornada jornada) {

        Equipo equipoLocal = jornada.getEquipoLocal();
        Equipo equipoVisitante = jornada.getEquipoVisitante();
        Temporada temporada = jornada.getTemporada();


        Clasificacion clasificacionLocal = clasificacionRepository.findByEquipoAndTemporada(equipoLocal, temporada);
        Clasificacion clasificacionVisitante = clasificacionRepository.findByEquipoAndTemporada(equipoVisitante, temporada);


        int golesLocal = jornada.getGolLocal();
        int golesVisitante = jornada.getGolVisitante();

        if(golesLocal > golesVisitante) {
            clasificacionLocal.setPuntos(clasificacionLocal.getPuntos() + 3);
            clasificacionLocal.setVictorias(clasificacionLocal.getVictorias() + 1);
            clasificacionVisitante.setDerrotas(clasificacionVisitante.getDerrotas() + 1);
        }else if(golesLocal == golesVisitante) {
            clasificacionLocal.setPuntos(clasificacionLocal.getPuntos() + 1);
            clasificacionVisitante.setPuntos(clasificacionVisitante.getPuntos() + 1);
            clasificacionLocal.setEmpates(clasificacionLocal.getEmpates() + 1);
            clasificacionVisitante.setEmpates(clasificacionVisitante.getEmpates() + 1);
        }else{
            clasificacionVisitante.setPuntos(clasificacionVisitante.getPuntos() + 3);
            clasificacionVisitante.setVictorias(clasificacionVisitante.getVictorias() + 1);
            clasificacionLocal.setDerrotas(clasificacionLocal.getDerrotas() + 1);
        }

        clasificacionLocal.setGolAFavor(clasificacionLocal.getGolAFavor() + golesLocal);
        clasificacionLocal.setGolEnContra(clasificacionLocal.getGolEnContra() + golesVisitante);
        clasificacionVisitante.setGolAFavor(clasificacionVisitante.getGolAFavor() + golesVisitante);
        clasificacionVisitante.setGolEnContra(clasificacionVisitante.getGolEnContra() + golesLocal);
        clasificacionLocal.setPartidosJugados(clasificacionLocal.getPartidosJugados() + 1);
        clasificacionVisitante.setPartidosJugados(clasificacionVisitante.getPartidosJugados() + 1);

        clasificacionLocal.setGolDiferencia(clasificacionLocal.getGolAFavor() - clasificacionLocal.getGolEnContra());
        clasificacionVisitante.setGolDiferencia(clasificacionVisitante.getGolAFavor() - clasificacionVisitante.getGolEnContra());

        clasificacionRepository.save(clasificacionLocal);
        clasificacionRepository.save(clasificacionVisitante);

        clasificacionService.actualizarPuestos();
    }


}
