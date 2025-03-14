package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.entity.Clasificacion;
import com.example.zonadegolbackend.repository.ClasificacionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClasificacionService {

    private final ClasificacionRepository clasificacionRepository;

    public List<Clasificacion> findAll() {
        return clasificacionRepository.findAll();
    }

    //FUNCA
    //GF - GC = GD
    //Victoria +3, Empate +1, Derrota +0
    public Clasificacion crearClasificacion(Clasificacion clasificacion) {

        Clasificacion nuevoClasificacion = new Clasificacion();

        nuevoClasificacion.setPuesto(clasificacion.getPuesto());
        nuevoClasificacion.setVictorias(clasificacion.getVictorias());
        nuevoClasificacion.setEmpates(clasificacion.getEmpates());
        nuevoClasificacion.setDerrotas(clasificacion.getDerrotas());
        nuevoClasificacion.setGolAFavor(clasificacion.getGolAFavor());
        nuevoClasificacion.setGolEnContra(clasificacion.getGolEnContra());
        nuevoClasificacion.setGolDiferencia(clasificacion.getGolDiferencia());
        nuevoClasificacion.setPuntos(clasificacion.getPuntos());
        nuevoClasificacion.setEquipoLiga(clasificacion.getEquipoLiga());

        return clasificacionRepository.save(nuevoClasificacion);

    }
}
