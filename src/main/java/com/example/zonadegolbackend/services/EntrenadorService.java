package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.dtos.CrearEntrenador;
import com.example.zonadegolbackend.entity.Entrenador;
import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.enums.Rol;
import com.example.zonadegolbackend.repository.EntrenadorRepository;
import com.example.zonadegolbackend.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;
    private final UsuarioRepository usuarioRepository;

    public Entrenador create(CrearEntrenador crearEntrenador) {
        Usuario usuario = new Usuario();
        usuario.setUsername(crearEntrenador.getUsername());
        usuario.setPassword(crearEntrenador.getPassword());
        usuario.setCorreo(crearEntrenador.getCorreo());
        usuario.setRol(Rol.ENTRENADOR);

        usuarioRepository.save(usuario);

        Entrenador entrenador = new Entrenador();
        entrenador.setNombre(crearEntrenador.getNombre());
        entrenador.setApellido(crearEntrenador.getApellido());
        entrenador.setFechaNacimiento(crearEntrenador.getFechaNacimiento());
        entrenador.setDni(crearEntrenador.getDni());
        entrenador.setImagen(crearEntrenador.getImagen());
        entrenador.setUsuario(usuario);

        return entrenadorRepository.save(entrenador);
    }


    public Entrenador update(Integer idEntrenador, CrearEntrenador crearEntrenador) {
        Entrenador entrenador = entrenadorRepository.findById(idEntrenador)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        Usuario usuario = entrenador.getUsuario();
        usuario.setUsername(crearEntrenador.getUsername());
        usuario.setPassword(crearEntrenador.getPassword());
        usuario.setCorreo(crearEntrenador.getCorreo());
        usuarioRepository.save(usuario);

        entrenador.setNombre(crearEntrenador.getNombre());
        entrenador.setApellido(crearEntrenador.getApellido());
        entrenador.setFechaNacimiento(crearEntrenador.getFechaNacimiento());
        entrenador.setDni(crearEntrenador.getDni());
        entrenador.setImagen(crearEntrenador.getImagen());
        entrenador.setUsuario(usuario);

        return entrenadorRepository.save(entrenador);
    }

    public void delete(Integer idEntrenador) {
        Entrenador entrenador = entrenadorRepository.findById(idEntrenador)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));
        entrenadorRepository.delete(entrenador);
    }

    public void deleteEyU(Integer idEntrenador) {
        Entrenador entrenador = entrenadorRepository.findById(idEntrenador)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));
        Usuario usuario = entrenador.getUsuario();
        entrenadorRepository.delete(entrenador);
        usuarioRepository.delete(usuario);
    }



}
