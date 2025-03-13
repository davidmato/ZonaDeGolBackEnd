package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.dtos.AuthenticationDTO;
import com.example.zonadegolbackend.dtos.UsuarioDto;
import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @PostMapping("/register")
    public AuthenticationDTO register(@RequestBody UsuarioDto userDTO) {
        return usuarioService.register(userDTO);
    }

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @PostMapping("/login")
    public AuthenticationDTO login(@RequestBody UsuarioDto usuarioDTO) {
        logger.debug("Login method called with usuarioDTO: {}", usuarioDTO);
        return usuarioService.login(usuarioDTO);
    }



    @PostMapping("/crear")
    public void crearUsuario(@RequestBody Usuario usuario) {
        usuarioService.crearUsuario(usuario);
    }

    @GetMapping("/buscar/{id}")
    public Usuario buscarUsuario(@PathVariable Integer id) {
        return usuarioService.getUsuarioById(id);
    }
}
