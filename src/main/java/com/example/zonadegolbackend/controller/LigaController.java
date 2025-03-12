package com.example.zonadegolbackend.controller;


import com.example.zonadegolbackend.entity.Liga;
import com.example.zonadegolbackend.entity.Temporada;
import com.example.zonadegolbackend.services.LigaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/liga")
public class LigaController {

    private final LigaService ligaService;

    @GetMapping("/all")
    public List<Liga> findAll() {
        return ligaService.findAll();
    }

    @PostMapping("/crear")
    public void crearLiga(@RequestBody Liga liga) {
        ligaService.crearLiga(liga);
    }
}
