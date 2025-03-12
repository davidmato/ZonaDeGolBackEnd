package com.example.zonadegolbackend.services;


import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.enums.Rol;
import com.example.zonadegolbackend.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }


    public void crearUsuario(Usuario usuario) {

        Usuario usuarioNuevo = new Usuario();

        usuarioNuevo.setUsername(usuario.getUsername());
        usuarioNuevo.setCorreo(usuario.getCorreo());
        usuarioNuevo.setPassword(usuario.getPassword());
        usuarioNuevo.setRol(Rol.JUGADOR);

        usuarioRepository.save(usuarioNuevo);
    }

}
