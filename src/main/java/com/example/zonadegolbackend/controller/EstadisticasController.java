package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.entity.Estadisticas;
import com.example.zonadegolbackend.services.EstadisticasService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/estadisticas")
public class EstadisticasController {

    private final EstadisticasService estadisticasService;

    @PostMapping("/crear")
    public Estadisticas crearEstadisticas(@RequestBody Estadisticas estadisticas) {
        return estadisticasService.crearEstadisticas(estadisticas);
    }

    @PutMapping("/editar/{id}")
    public Estadisticas editarEstadisticas(@PathVariable Integer id, @RequestBody Estadisticas estadisticas) {
        return estadisticasService.editarEstadisticas(id, estadisticas);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarEstadisticas(@PathVariable Integer id) {
        estadisticasService.eliminarEstadisticas(id);
    }
}
