package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.dtos.GenerarJornadaDTO;
import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Jornada;
import com.example.zonadegolbackend.entity.Temporada;
import com.example.zonadegolbackend.services.JornadaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jornada")
public class JornadaController {

    private final JornadaService jornadaService;

    @GetMapping("/listar")
    public List<Jornada> findAll() {
        return jornadaService.findAll();
    }

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

    @PostMapping("/generar")
    public ResponseEntity<List<Jornada>> generarJornadas(@RequestBody GenerarJornadaDTO request) {
        List<Jornada> jornadas = jornadaService.generarJornadas(request.getEquipos(), request.getTemporada());
        return ResponseEntity.ok(jornadas);
    }
}
