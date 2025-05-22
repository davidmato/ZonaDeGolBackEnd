package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.dtos.*;
import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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



    @GetMapping("/admin/arbitros")
    public List<Usuario> obtenerTodosLosArbitros() {
        return usuarioService.FindAllArbitros();
    }

    @PostMapping("/admin/crear/arbitro")
    public Usuario crearArbitro(@RequestBody ArbitroDTO usuario) {
        return usuarioService.CrearArbitro(usuario);
    }

    @PutMapping("/admin/editar/arbitro/{id}")
    public Usuario editarArbitro(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return usuarioService.editarArbitro(id, usuario);
    }

    @DeleteMapping("/admin/eliminar/usuario/{id}")
    public void eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
    }


    @PostMapping("/solicitar-restablecimiento")
    public void solicitarRestablecimientoPassword(@RequestBody CorreoDTO correo) {
        usuarioService.solicitarRestablecimientoPassword(correo.getCorreo());
    }

    @PostMapping("/restablecer")
    public void restablecerPassword(@RequestBody RestablecerContraseniaDTO request) {
        usuarioService.restablecerPassword(request.getToken(), request.getNewPassword());
    }


    @GetMapping("/pagado/{id}")
    public boolean usuarioHaPagado(@PathVariable Integer id) {
        return usuarioService.usuarioHaPagado(id);
    }

    //
    //    @GetMapping("/restablecer")
    //    public String mostrarFormularioRestablecer(@RequestParam String token) {
    //        // Aquí puedes devolver una vista, un mensaje o simplemente validar el token
    //        return "Token recibido: " + token;
    //    }
}
