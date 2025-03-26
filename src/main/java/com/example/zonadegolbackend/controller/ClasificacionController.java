package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.entity.Clasificacion;
import com.example.zonadegolbackend.services.ClasificacionService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//BUG
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clasificacion")
public class ClasificacionController {


    private final ClasificacionService clasificacionService;

    @GetMapping("/all")
    public List<Clasificacion> findAll() {
        return clasificacionService.findAll();
    }

//    @PostMapping("/crear")
//    public Clasificacion crearClasificacion(@RequestBody Clasificacion clasificacion) {
//        return clasificacionService.crearClasificacion(clasificacion);
//    }
//
//    @PutMapping("/editar/{id}")
//    public ResponseEntity<Clasificacion> editarClasificacion(@PathVariable Integer id, @RequestBody Clasificacion clasificacionActualizada) {
//        Clasificacion clasificacionEditada = clasificacionService.editarClasificacion(id, clasificacionActualizada);
//        return ResponseEntity.ok(clasificacionEditada);
//    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarClasificacion(@PathVariable Integer id) {
        clasificacionService.eliminarClasificacion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/por-liga-y-temporada")
    public List<Clasificacion> obtenerClasificacion(@RequestParam Integer ligaId, @RequestParam Integer temporadaId) {
        clasificacionService.actualizarPuestosYObtenerClasificacion(ligaId, temporadaId);
        return clasificacionService.obtenerClasificacion(ligaId, temporadaId);
    }

//    @GetMapping("/{idLiga}")
//    public List<Map<String, Object>> obtenerClasificacion(@PathVariable int idLiga) {
//        return clasificacionService.obtenerClasificacionPorLiga(idLiga);
//    }
}
