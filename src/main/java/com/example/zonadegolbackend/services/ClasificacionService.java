package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.dtos.ClasificacionDTO;
import com.example.zonadegolbackend.entity.Clasificacion;
import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Jornada;
import com.example.zonadegolbackend.entity.Temporada;
import com.example.zonadegolbackend.repository.ClasificacionRepository;
import com.example.zonadegolbackend.repository.JornadaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClasificacionService {

    private final ClasificacionRepository clasificacionRepository;
    private final JornadaRepository jornadaRepository;

    public List<Clasificacion> findAll() {
        return clasificacionRepository.findAll();
    }


    public void actualizarPuestos() {

        List<Clasificacion> clasificaciones = clasificacionRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Clasificacion::getPuntos)
                        .thenComparingInt(Clasificacion::getGolDiferencia)
                        .reversed())
                .collect(Collectors.toList());

        for (int i = 0; i < clasificaciones.size(); i++) {
            clasificaciones.get(i).setPuesto(i + 1);
        }

        clasificacionRepository.saveAll(clasificaciones);
    }

    public Clasificacion crearClasificacion(Equipo equipo, Temporada temporada) {
        Clasificacion nuevaClasificacion = new Clasificacion();

        nuevaClasificacion.setPuesto(0);
        nuevaClasificacion.setVictorias(0);
        nuevaClasificacion.setEmpates(0);
        nuevaClasificacion.setDerrotas(0);
        nuevaClasificacion.setGolAFavor(0);
        nuevaClasificacion.setGolEnContra(0);
        nuevaClasificacion.setGolDiferencia(0);
        nuevaClasificacion.setPuntos(0);
        nuevaClasificacion.setPartidosJugados(0);

        nuevaClasificacion.setEquipo(equipo);
        nuevaClasificacion.setTemporada(temporada);

        clasificacionRepository.save(nuevaClasificacion);
        return nuevaClasificacion;
    }

    public Clasificacion editarClasificacion(Integer id, Clasificacion clasificacionActualizada) {
        Clasificacion clasificacionExistente = clasificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clasificacion not found"));

        clasificacionExistente.setPuesto(clasificacionActualizada.getPuesto());
        clasificacionExistente.setVictorias(clasificacionActualizada.getVictorias());
        clasificacionExistente.setEmpates(clasificacionActualizada.getEmpates());
        clasificacionExistente.setDerrotas(clasificacionActualizada.getDerrotas());
        clasificacionExistente.setGolAFavor(clasificacionActualizada.getGolAFavor());
        clasificacionExistente.setGolEnContra(clasificacionActualizada.getGolEnContra());
        clasificacionExistente.setGolDiferencia(clasificacionActualizada.getGolDiferencia());
        clasificacionExistente.setPuntos(clasificacionActualizada.getPuntos());
        clasificacionExistente.setPartidosJugados(clasificacionActualizada.getPartidosJugados());

        clasificacionExistente.setEquipo(clasificacionActualizada.getEquipo());
        clasificacionExistente.setTemporada(clasificacionActualizada.getTemporada());

        clasificacionRepository.save(clasificacionExistente);
        return clasificacionExistente;
    }


    public void eliminarClasificacion(Integer id) {
        Clasificacion clasificacionExistente = clasificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clasificacion not found"));
        clasificacionRepository.delete(clasificacionExistente);
        actualizarPuestos();
    }



    public List<Clasificacion> obtenerClasificacion(Integer ligaId, Integer temporadaId) {
        return clasificacionRepository.findByEquipo_Liga_IdAndTemporada_Id(ligaId, temporadaId)
                .stream()
                .sorted(Comparator.comparingInt(Clasificacion::getPuesto))
                .collect(Collectors.toList());    }

    public void actualizarPuestosYObtenerClasificacion(Integer ligaId, Integer temporadaId) {
        List<Clasificacion> clasificaciones = clasificacionRepository.findByEquipo_Liga_IdAndTemporada_Id(ligaId, temporadaId)
                .stream()
                .sorted(Comparator.comparingInt(Clasificacion::getPuntos)
                        .thenComparingInt(Clasificacion::getGolDiferencia)
                        .reversed())
                .collect(Collectors.toList());

        for (int i = 0; i < clasificaciones.size(); i++) {
            clasificaciones.get(i).setPuesto(i + 1);
        }

        clasificacionRepository.saveAll(clasificaciones);
    }

    public List<ClasificacionDTO> obtenerClasificacionConForma(Integer ligaId, Integer temporadaId) {
        List<Clasificacion> clasificaciones = clasificacionRepository.findByEquipo_Liga_IdAndTemporada_Id(ligaId, temporadaId)
                .stream()
                .sorted(Comparator.comparingInt(Clasificacion::getPuesto))
                .collect(Collectors.toList());

        return clasificaciones.stream().map(c -> {
            ClasificacionDTO dto = new ClasificacionDTO();
            dto.setPuesto(c.getPuesto());
            dto.setNombre(c.getEquipo().getNombre());
            dto.setPartidosJugados(c.getPartidosJugados());
            dto.setVictorias(c.getVictorias());
            dto.setEmpates(c.getEmpates());
            dto.setDerrotas(c.getDerrotas());
            dto.setGolAFavor(c.getGolAFavor());
            dto.setGolEnContra(c.getGolEnContra());
            dto.setGolDiferencia(c.getGolDiferencia());
            dto.setImagenEquipo(c.getEquipo().getImagen());
            dto.setPuntos(c.getPuntos());


            List<Jornada> ultimos5 = jornadaRepository.findLast5ByEquipoAndTemporada(
                    c.getEquipo(), c.getTemporada(), PageRequest.of(0, 5));

            List<String> forma = ultimos5.stream().map(j -> {
                int golesEquipo, golesRival;
                boolean esLocal = j.getEquipoLocal().getId().equals(c.getEquipo().getId());

                if (esLocal) {
                    golesEquipo = j.getGolLocal();
                    golesRival = j.getGolVisitante();
                } else {
                    golesEquipo = j.getGolVisitante();
                    golesRival = j.getGolLocal();
                }

                if (golesEquipo > golesRival) return "✅";
                else if (golesEquipo == golesRival) return "➖";
                else return "❌";
            }).toList();

            dto.setForma(forma);

            return dto;
        }).toList();
    }

    public Map<String, Long> obtenerTotalGolesPorEntrenador(Integer entrenadorId) {
        return clasificacionRepository.findTotalGolesByEntrenadorId(entrenadorId);
    }

}
