package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.dtos.EstadioDTO;
import com.example.zonadegolbackend.entity.Estadio;
import com.example.zonadegolbackend.services.EstadioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/estadio")
public class EstadioController {

    private final EstadioService estadioService;

    @PostMapping("/crear")
    public EstadioDTO nuevoEstadio(@RequestBody EstadioDTO estadio) {return estadioService.crearEstadio(estadio);}

    @PutMapping("/editar/{id}")
    public EstadioDTO actualizarEstadio(@PathVariable Integer id, @RequestBody EstadioDTO estadioActualizado) {
        return estadioService.editarEstadio(id, estadioActualizado);
    }

    @DeleteMapping("/eliminar/{id}")
    public void borrarEstadio(@PathVariable Integer id) {
        estadioService.eliminarEstadio(id);
    }
}
