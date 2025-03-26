package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.entity.Jugador;
import com.example.zonadegolbackend.services.JugadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jugador")
public class JugadorController {

    private final JugadorService jugadorService;

    @GetMapping("/all")
    public List<Jugador> findAll() {
        return jugadorService.findAll();
    }

    @PostMapping("/crear")
    public Jugador crearJugador(@RequestBody Jugador jugador) {
       return jugadorService.crearJugador(jugador);
    }
//
//    @PostMapping("/{idEquipo}/jugadores")
//    public void associatePlayersWithTeam(@PathVariable Integer idEquipo, @RequestBody List<Integer> idJugadores) {
//        jugadorService.associatePlayersWithTeam(idEquipo, idJugadores);
//    }


}
