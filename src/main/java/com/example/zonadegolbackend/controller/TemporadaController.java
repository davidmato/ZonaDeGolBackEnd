package com.example.zonadegolbackend.controller;


import com.example.zonadegolbackend.entity.Temporada;
import com.example.zonadegolbackend.entity.TemporadaLiga;
import com.example.zonadegolbackend.services.TemporadaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/temporadas")
public class TemporadaController {

    private final TemporadaService temporadaService;

    @GetMapping("/all")
    public List<Temporada> findAll() {
        return temporadaService.findAll();
    }

    @PostMapping("/crear")
    public Temporada crearTemporada(@RequestBody Temporada temporada) {
        return temporadaService.crearTemporada(temporada);
    }

    @PutMapping("/editar/{id}")
    public Temporada editarTemporada(@PathVariable Integer id, @RequestBody Temporada temporada) {
        return temporadaService.editarTemporada(id, temporada);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarTemporada(@PathVariable Integer id) {
        temporadaService.eliminarTemporada(id);
    }

    @GetMapping("/buscarTemporadaLiga")
    public TemporadaLiga buscarTemporadaLiga(@RequestParam Integer temporadaId, @RequestParam Integer ligaId) {
        return temporadaService.buscarTemporadaLiga(temporadaId, ligaId);
    }
}
