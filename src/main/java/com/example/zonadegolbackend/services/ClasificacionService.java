package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.entity.Clasificacion;
import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Temporada;
import com.example.zonadegolbackend.repository.ClasificacionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClasificacionService {

    private final ClasificacionRepository clasificacionRepository;

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
//
//
//    public Clasificacion crearClasificacion(Clasificacion clasificacion) {
//
//        Clasificacion nuevoClasificacion = new Clasificacion();
//
//        nuevoClasificacion.setVictorias(clasificacion.getVictorias());
//        nuevoClasificacion.setEmpates(clasificacion.getEmpates());
//        nuevoClasificacion.setDerrotas(clasificacion.getDerrotas());
//        nuevoClasificacion.setGolAFavor(clasificacion.getGolAFavor());
//        nuevoClasificacion.setGolEnContra(clasificacion.getGolEnContra());
//
//        int golDiferencia = clasificacion.getGolAFavor() - clasificacion.getGolEnContra();
//        nuevoClasificacion.setGolDiferencia(golDiferencia);
//
//        int puntos = (clasificacion.getVictorias() * 3) + (clasificacion.getEmpates());
//        nuevoClasificacion.setPuntos(puntos);
//
//        nuevoClasificacion.setEquipoLiga(clasificacion.getEquipoLiga());
//
//        nuevoClasificacion.setPuesto(0);
//
//        clasificacionRepository.save(nuevoClasificacion);
//
//        actualizarPuestos();
//
//        return nuevoClasificacion;
//    }
//
//    public Clasificacion editarClasificacion(Integer id, Clasificacion clasificacionActualizada) {
//        Clasificacion clasificacionExistente = clasificacionRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Clasificacion not found"));
//
//        clasificacionExistente.setVictorias(clasificacionActualizada.getVictorias());
//        clasificacionExistente.setEmpates(clasificacionActualizada.getEmpates());
//        clasificacionExistente.setDerrotas(clasificacionActualizada.getDerrotas());
//        clasificacionExistente.setGolAFavor(clasificacionActualizada.getGolAFavor());
//        clasificacionExistente.setGolEnContra(clasificacionActualizada.getGolEnContra());
//
//        int golDiferencia = clasificacionActualizada.getGolAFavor() - clasificacionActualizada.getGolEnContra();
//        clasificacionExistente.setGolDiferencia(golDiferencia);
//
//        int puntos = (clasificacionActualizada.getVictorias() * 3) + (clasificacionActualizada.getEmpates());
//        clasificacionExistente.setPuntos(puntos);
//
//        clasificacionExistente.setEquipoLiga(clasificacionActualizada.getEquipoLiga());
//
//        clasificacionRepository.save(clasificacionExistente);
//
//        actualizarPuestos();
//
//        return clasificacionExistente;
//    }

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


//    public List<Map<String, Object>> obtenerClasificacionPorLiga(int idLiga) {
//        List<Object[]> resultados = clasificacionRepository.obtenerClasificacion(idLiga);
//
//        return resultados.stream().map(obj -> Map.of(
//                "equipoNombre", obj[0],
//                "puesto", obj[1],
//                "victorias", obj[2],
//                "empates", obj[3],
//                "derrotas", obj[4],
//                "golesAFavor", obj[5],
//                "golesEnContra", obj[6],
//                "golesDiferencia", obj[7],
//                "puntos", obj[8]
//        )).collect(Collectors.toList());
//    }

}
