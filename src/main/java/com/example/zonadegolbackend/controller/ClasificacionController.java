package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.entity.Clasificacion;
import com.example.zonadegolbackend.services.ClasificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clasificacion")
public class ClasificacionController {


    private final ClasificacionService clasificacionService;

    @GetMapping("/all")
    public List<Clasificacion> findAll() {
        return clasificacionService.findAll();
    }

    @PostMapping("/crear")
    public Clasificacion crearClasificacion(@RequestBody Clasificacion clasificacion) {
        return clasificacionService.crearClasificacion(clasificacion);
    }
}
