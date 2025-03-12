package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/crear")
    public void crearUsuario(@RequestBody Usuario usuario) {
        usuarioService.crearUsuario(usuario);
    }

    @GetMapping("/buscar/{id}")
    public Usuario buscarUsuario(@PathVariable Integer id) {
        return usuarioService.getUsuarioById(id);
    }
}
