package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.entity.Estadio;
import com.example.zonadegolbackend.services.EstadioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/estadio")
public class EstadioController {

    private final EstadioService estadioService;

    @PostMapping("/admin/crear")
    public Estadio nuevoEstadio(@RequestBody Estadio estadio) {return estadioService.crearEstadio(estadio);}

    @PutMapping("/admin/editar/{id}")
    public Estadio actualizarEstadio(@PathVariable Integer id, @RequestBody Estadio estadioActualizado) {
        return estadioService.editarEstadio(id, estadioActualizado);
    }

    @DeleteMapping("/admin/eliminar/{id}")
    public void borrarEstadio(@PathVariable Integer id) {
        estadioService.eliminarEstadio(id);
    }
}
