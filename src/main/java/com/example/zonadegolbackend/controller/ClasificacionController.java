package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.dtos.ClasificacionDTO;
import com.example.zonadegolbackend.dtos.CrearClasificacionDTO;
import com.example.zonadegolbackend.entity.Clasificacion;
import com.example.zonadegolbackend.services.ClasificacionService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clasificacion")
public class ClasificacionController {


    private final ClasificacionService clasificacionService;

    @GetMapping("/all")
    public List<Clasificacion> findAll() {
        return clasificacionService.findAll();
    }

    @PostMapping("/crear")
    public Clasificacion crearClasificacion(@RequestBody CrearClasificacionDTO request) {
        return clasificacionService.crearClasificacion(request.getEquipo(), request.getTemporada());
    }

    @PutMapping("/editar/{id}")
    public Clasificacion editarClasificacion(@PathVariable Integer id, @RequestBody Clasificacion clasificacionActualizada) {
        return clasificacionService.editarClasificacion(id, clasificacionActualizada);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarClasificacion(@PathVariable Integer id) {
        clasificacionService.eliminarClasificacion(id);
    }

    @GetMapping("/buscar/liga/temporada/{ligaId}/{temporadaId}")
    public List<Clasificacion> obtenerClasificacion(@PathVariable Integer ligaId, @PathVariable Integer temporadaId) {
        clasificacionService.actualizarPuestosYObtenerClasificacion(ligaId, temporadaId);
        return clasificacionService.obtenerClasificacion(ligaId, temporadaId);
    }

//    @GetMapping("/{idLiga}")
//    public List<Map<String, Object>> obtenerClasificacion(@PathVariable int idLiga) {
//        return clasificacionService.obtenerClasificacionPorLiga(idLiga);
//    }

    @GetMapping("/ultimosCinco")
    public List<ClasificacionDTO> obtenerClasificacionDTO(@RequestParam Integer ligaId, @RequestParam Integer temporadaId) {
        return clasificacionService.obtenerClasificacionConForma(ligaId, temporadaId);
    }

}
