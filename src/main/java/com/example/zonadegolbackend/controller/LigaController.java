package com.example.zonadegolbackend.controller;


import com.example.zonadegolbackend.dtos.JugadorDTO;
import com.example.zonadegolbackend.dtos.LigaClasificacionHomeDTO;
import com.example.zonadegolbackend.entity.*;
import com.example.zonadegolbackend.repository.LigaRepository;
import com.example.zonadegolbackend.services.LigaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/liga")
public class LigaController {

    private final LigaService ligaService;

    @GetMapping("/all")
    public List<Liga> findAll() {
        return ligaService.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Liga buscarById(@PathVariable Integer id) {
       return ligaService.findById(id);
    }

    @PostMapping("/admin/crear")
    public Liga crearLiga(@RequestBody Liga liga) {
        return ligaService.crearLiga(liga);
    }

    @PutMapping("/admin/editar/{id}")
    public Liga editarLiga(@PathVariable Integer id, @RequestBody Liga liga) {
        return ligaService.editarLiga(id, liga);
    }

    @DeleteMapping("/admin/eliminar/{id}")
    public void eliminarLiga(@PathVariable Integer id) {
        ligaService.eliminarLiga(id);
    }

    @GetMapping("/equipos/{ligaId}")
    public List<Equipo> getEquiposByLigaId(@PathVariable Integer ligaId) {
        return ligaService.findByLigaId(ligaId);
    }

    @GetMapping("/clasificacion/{ligaId}")
    public List<Clasificacion> obtenerClasificacionLigaTemporadaReciente(@PathVariable Integer ligaId) {
        return ligaService.obtenerClasificacionLigaTemporadaReciente(ligaId);
    }

    @GetMapping("/random-clasificacion")
    public LigaClasificacionHomeDTO getLigaConClasificacionAleatoria() {
        return ligaService.getLigaConClasificacionAleatoriaDTO();
    }
}
