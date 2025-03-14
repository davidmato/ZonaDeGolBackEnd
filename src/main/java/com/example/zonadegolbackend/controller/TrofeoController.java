package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.entity.Temporada;
import com.example.zonadegolbackend.entity.Trofeo;
import com.example.zonadegolbackend.services.TrofeoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trofeo")
public class TrofeoController {

    private final TrofeoService trofeoService;

    @GetMapping("/all")
    public List<Trofeo> findAll() {
        return trofeoService.findAll();
    }

    @PostMapping("/crear")
    public Trofeo crearTrofeo(@RequestBody Trofeo trofeo) {
        return trofeoService.crearTrofeo(trofeo);
    }
}
