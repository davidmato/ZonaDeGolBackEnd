package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.dtos.EstadioDTO;
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

    public EstadioDTO crearEstadio(EstadioDTO estadioDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede crear un estadio");
        }

        Estadio estadioNuevo = new Estadio();
        estadioNuevo.setNombre(estadioDTO.getNombre());
        estadioNuevo.setDireccion(estadioDTO.getDireccion());
        estadioNuevo.setAforo(estadioDTO.getAforo());

        Estadio estadioGuardado = estadioRepository.save(estadioNuevo);

        return new EstadioDTO(estadioGuardado.getId(), estadioGuardado.getNombre(), estadioGuardado.getDireccion(), estadioGuardado.getAforo());
    }

    public EstadioDTO editarEstadio(Integer id, EstadioDTO estadioDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede editar un estadio");
        }

        Estadio estadioExistente = estadioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estadio no encontrado"));

        estadioExistente.setNombre(estadioDTO.getNombre());
        estadioExistente.setDireccion(estadioDTO.getDireccion());
        estadioExistente.setAforo(estadioDTO.getAforo());

        Estadio estadioActualizado = estadioRepository.save(estadioExistente);

        return new EstadioDTO(estadioActualizado.getId(), estadioActualizado.getNombre(), estadioActualizado.getDireccion(), estadioActualizado.getAforo());
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
