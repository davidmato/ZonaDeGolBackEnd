package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.entity.Estadio;
import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.enums.Rol;
import com.example.zonadegolbackend.repository.EstadioRepository;
import com.example.zonadegolbackend.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EstadioService {

    private final EstadioRepository estadioRepository;
    private final UsuarioRepository usuarioRepository;

    public Estadio crearEstadio(Estadio estadio) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede crear un estadio");
        }

        Estadio estadioNuevo = new Estadio();
        estadioNuevo.setNombre(estadio.getNombre());
        estadioNuevo.setDireccion(estadio.getDireccion());
        estadioNuevo.setAforo(estadio.getAforo());

        return estadioRepository.save(estadioNuevo);
    }

    public Estadio editarEstadio(Integer id, Estadio estadio) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede editar un estadio");
        }

        Estadio estadioExistente = estadioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estadio no encontrado"));

        estadioExistente.setNombre(estadio.getNombre());
        estadioExistente.setDireccion(estadio.getDireccion());
        estadioExistente.setAforo(estadio.getAforo());

        return estadioRepository.save(estadioExistente);
    }

    public void eliminarEstadio(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede eliminar un estadio");
        }

        estadioRepository.deleteById(id);
    }

}
