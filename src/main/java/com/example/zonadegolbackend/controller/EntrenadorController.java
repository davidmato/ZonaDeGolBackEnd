package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.dtos.CrearEntrenador;
import com.example.zonadegolbackend.dtos.CrearEquipo;
import com.example.zonadegolbackend.dtos.CrearJugador;
import com.example.zonadegolbackend.entity.Entrenador;
import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Jugador;
import com.example.zonadegolbackend.services.EntrenadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/entrenador")
public class EntrenadorController {


    private final EntrenadorService entrenadorService;

    @PostMapping("/crear")
    public Entrenador crearEntrenador(@RequestBody CrearEntrenador crearEntrenador) {
        return entrenadorService.create(crearEntrenador);
    }

    @PutMapping("/editar/{idEntrenador}")
    public Entrenador editarEntrenador(@PathVariable Integer idEntrenador, @RequestBody CrearEntrenador crearEntrenador) {
        return entrenadorService.update(idEntrenador, crearEntrenador);
    }

    @DeleteMapping("/eliminar/{idEntrenador}")
    public void eliminarEntrenador(@PathVariable Integer idEntrenador) {
        entrenadorService.delete(idEntrenador);
    }

    @PostMapping("/crear/equipo")
    public Equipo crearEquipo(@RequestBody CrearEquipo crearEquipo) {
       return entrenadorService.createEquipo(crearEquipo);
    }


    @PostMapping("crear/jugador")
    public ResponseEntity<Jugador> createJugador(@RequestBody CrearJugador crearJugador) {
        Jugador jugador = entrenadorService.createJugador(crearJugador);
        return ResponseEntity.ok(jugador);
    }
}
