package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.dtos.EstadioDTO;
import com.example.zonadegolbackend.entity.Estadio;
import com.example.zonadegolbackend.repository.EstadioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EstadioService {

    private final EstadioRepository estadioRepository;

    public EstadioDTO crearEstadio(EstadioDTO estadioDTO) {

        Estadio estadioNuevo = new Estadio();
        estadioNuevo.setNombre(estadioDTO.getNombre());
        estadioNuevo.setDireccion(estadioDTO.getDireccion());
        estadioNuevo.setAforo(estadioDTO.getAforo());

        Estadio estadioGuardado = estadioRepository.save(estadioNuevo);

        return new EstadioDTO(estadioGuardado.getId(), estadioGuardado.getNombre(), estadioGuardado.getDireccion(), estadioGuardado.getAforo());
    }

    public EstadioDTO editarEstadio(Integer id, EstadioDTO estadioDTO) {

        Estadio estadioExistente = estadioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estadio no encontrado"));

        estadioExistente.setNombre(estadioDTO.getNombre());
        estadioExistente.setDireccion(estadioDTO.getDireccion());
        estadioExistente.setAforo(estadioDTO.getAforo());

        Estadio estadioActualizado = estadioRepository.save(estadioExistente);

        return new EstadioDTO(estadioActualizado.getId(), estadioActualizado.getNombre(), estadioActualizado.getDireccion(), estadioActualizado.getAforo());
    }

    public void eliminarEstadio(Integer id) {
        estadioRepository.deleteById(id);
    }

}
