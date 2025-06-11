package com.example.zonadegolbackend.controller;


import com.example.zonadegolbackend.entity.Noticias;
import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.services.NoticiasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/admin/crear")
    public Noticias crearNoticia(@RequestBody Noticias noticia) { return noticiasService.crearNoticia(noticia);
    }

    @PutMapping("/admin/editar/{id}")
    public Noticias editarNoticia(@PathVariable Integer id, @RequestBody Noticias noticia) {
        return noticiasService.editarNoticia(id, noticia);
    }

    @DeleteMapping("/admin/eliminar/{id}")
    public void eliminarNoticia(@PathVariable Integer id) {
        noticiasService.eliminarNoticia(id);
    }

    @GetMapping("/top")
    public List<Noticias> getTop3Noticias() {
        return noticiasService.findTop3();
    }

}
