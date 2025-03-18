package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.entity.Clasificacion;
import com.example.zonadegolbackend.repository.ClasificacionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
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


    public Clasificacion crearClasificacion(Clasificacion clasificacion) {

        Clasificacion nuevoClasificacion = new Clasificacion();

        nuevoClasificacion.setVictorias(clasificacion.getVictorias());
        nuevoClasificacion.setEmpates(clasificacion.getEmpates());
        nuevoClasificacion.setDerrotas(clasificacion.getDerrotas());
        nuevoClasificacion.setGolAFavor(clasificacion.getGolAFavor());
        nuevoClasificacion.setGolEnContra(clasificacion.getGolEnContra());

        int golDiferencia = clasificacion.getGolAFavor() - clasificacion.getGolEnContra();
        nuevoClasificacion.setGolDiferencia(golDiferencia);

        int puntos = (clasificacion.getVictorias() * 3) + (clasificacion.getEmpates());
        nuevoClasificacion.setPuntos(puntos);

        nuevoClasificacion.setEquipoLiga(clasificacion.getEquipoLiga());

        nuevoClasificacion.setPuesto(0);

        clasificacionRepository.save(nuevoClasificacion);

        actualizarPuestos();

        return nuevoClasificacion;
    }

    public Clasificacion editarClasificacion(Integer id, Clasificacion clasificacionActualizada) {
        Clasificacion clasificacionExistente = clasificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clasificacion not found"));

        clasificacionExistente.setVictorias(clasificacionActualizada.getVictorias());
        clasificacionExistente.setEmpates(clasificacionActualizada.getEmpates());
        clasificacionExistente.setDerrotas(clasificacionActualizada.getDerrotas());
        clasificacionExistente.setGolAFavor(clasificacionActualizada.getGolAFavor());
        clasificacionExistente.setGolEnContra(clasificacionActualizada.getGolEnContra());

        int golDiferencia = clasificacionActualizada.getGolAFavor() - clasificacionActualizada.getGolEnContra();
        clasificacionExistente.setGolDiferencia(golDiferencia);

        int puntos = (clasificacionActualizada.getVictorias() * 3) + (clasificacionActualizada.getEmpates());
        clasificacionExistente.setPuntos(puntos);

        clasificacionExistente.setEquipoLiga(clasificacionActualizada.getEquipoLiga());

        clasificacionRepository.save(clasificacionExistente);

        actualizarPuestos();

        return clasificacionExistente;
    }

    public void eliminarClasificacion(Integer id) {
        Clasificacion clasificacionExistente = clasificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clasificacion not found"));
        clasificacionRepository.delete(clasificacionExistente);
        actualizarPuestos();
    }

}
