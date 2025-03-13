package com.example.zonadegolbackend.controller;


import com.example.zonadegolbackend.entity.Noticias;
import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.services.NoticiasService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/noticias")

public class NoticiasController {

    private final NoticiasService noticiasService;

    @GetMapping("/all")
    public List<Noticias> findAll() {
        return noticiasService.findAll();
    }

    @PostMapping("/crear")
    public Noticias crearNoticia(@RequestBody Noticias noticia) { return noticiasService.crearNoticia(noticia);
    }
}
