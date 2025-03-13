package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.entity.Jugador;
import com.example.zonadegolbackend.services.JugadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jugador")
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
}
