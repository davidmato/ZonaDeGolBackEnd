package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.entity.Jornada;
import com.example.zonadegolbackend.services.JornadaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jornada")
public class JornadaController {

    private final JornadaService jornadaService;

    @PostMapping("/crear")
    public Jornada crearJornada(@RequestBody Jornada jornada) {
        return jornadaService.crearJornada(jornada);
    }

    @PutMapping("/editar/{id}")
    public Jornada editarJornada(@PathVariable Integer id, @RequestBody Jornada jornada) {
        return jornadaService.editarJornada(id, jornada);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarJornada(@PathVariable Integer id) {
        jornadaService.eliminarJornada(id);
    }
}
