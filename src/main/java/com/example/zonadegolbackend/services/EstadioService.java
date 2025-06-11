package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.entity.Estadio;
import com.example.zonadegolbackend.repository.EstadioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EstadioService {

    private final EstadioRepository estadioRepository;

    public Estadio crearEstadio(Estadio estadio) {

        Estadio estadioNuevo = new Estadio();
        estadioNuevo.setNombre(estadio.getNombre());
        estadioNuevo.setDireccion(estadio.getDireccion());
        estadioNuevo.setAforo(estadio.getAforo());

        return estadioRepository.save(estadioNuevo);
    }

    public Estadio editarEstadio(Integer id, Estadio estadio) {

        Estadio estadioExistente = estadioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estadio no encontrado"));

        estadioExistente.setNombre(estadio.getNombre());
        estadioExistente.setDireccion(estadio.getDireccion());
        estadioExistente.setAforo(estadio.getAforo());

        return estadioRepository.save(estadioExistente);
    }

    public void eliminarEstadio(Integer id) {
        estadioRepository.deleteById(id);
    }

}
