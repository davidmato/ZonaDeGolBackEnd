package com.example.zonadegolbackend.controller;


import com.example.zonadegolbackend.entity.Liga;
import com.example.zonadegolbackend.entity.Temporada;
import com.example.zonadegolbackend.entity.Trofeo;
import com.example.zonadegolbackend.repository.LigaRepository;
import com.example.zonadegolbackend.services.LigaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/liga")
public class LigaController {

    private final LigaService ligaService;
    private final LigaRepository ligaRepository;

    @GetMapping("/all")
    public List<Liga> findAll() {
        return ligaService.findAll();
    }

//    @PostMapping("/crear")
//    public ResponseEntity<Liga> crearLiga(@RequestBody Liga liga) {
//        Liga savedLiga = ligaService.crearLiga(liga);
//        return ResponseEntity.ok(savedLiga);
//    }

    @PostMapping("/crear")
    public ResponseEntity<Liga> crearLiga(@RequestBody Liga liga) {
        Liga savedLiga = ligaService.crearLiga(liga);
        return ResponseEntity.ok(savedLiga);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Liga> editarLiga(@PathVariable Integer id, @RequestBody Liga liga) {
        Liga ligaEditada = ligaService.editarLiga(id, liga);
        return ResponseEntity.ok(ligaEditada);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Liga> eliminarLiga(@PathVariable Integer id) {
        ligaService.eliminarLiga(id);
        return ResponseEntity.noContent().build();
    }
}
