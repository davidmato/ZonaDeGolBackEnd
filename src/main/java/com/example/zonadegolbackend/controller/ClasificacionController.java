package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.dtos.ClasificacionDTO;
import com.example.zonadegolbackend.dtos.CrearClasificacionDTO;
import com.example.zonadegolbackend.entity.Clasificacion;
import com.example.zonadegolbackend.services.ClasificacionService;
import lombok.RequiredArgsConstructor;
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
    public List<ClasificacionDTO> obtenerClasificacion(@PathVariable Integer ligaId, @PathVariable Integer temporadaId) {
        clasificacionService.actualizarPuestosYObtenerClasificacion(ligaId, temporadaId);
        return clasificacionService.obtenerClasificacionConForma(ligaId, temporadaId);
    }


    @GetMapping("/ultimosCinco")
    public List<ClasificacionDTO> obtenerClasificacionDTO(@RequestParam Integer ligaId, @RequestParam Integer temporadaId) {
        return clasificacionService.obtenerClasificacionConForma(ligaId, temporadaId);
    }

    @GetMapping("/goles/entrenador/{entrenadorId}")
    public Map<String, Long> obtenerTotalGolesPorEntrenador(@PathVariable Integer entrenadorId) {
        return clasificacionService.obtenerTotalGolesPorEntrenador(entrenadorId);
    }

}
