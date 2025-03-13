package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.dtos.CrearEntrenador;
import com.example.zonadegolbackend.entity.Entrenador;
import com.example.zonadegolbackend.services.EntrenadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/entrenador")
public class EntrenadorController {


    private final EntrenadorService entrenadorService;

    @PostMapping("/crear")
    public Entrenador crearEntrenador(@RequestBody CrearEntrenador crearEntrenador) {
        return entrenadorService.create(crearEntrenador);
    }
}
