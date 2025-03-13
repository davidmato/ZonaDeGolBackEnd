package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.dtos.CrearEquipo;
import com.example.zonadegolbackend.entity.Entrenador;
import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.enums.Rol;
import com.example.zonadegolbackend.repository.EntrenadorRepository;
import com.example.zonadegolbackend.repository.EquipoRepository;
import com.example.zonadegolbackend.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EquipoService {

    private final EquipoRepository equipoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EntrenadorRepository entrenadorRepository;

    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }



    public Equipo create(CrearEquipo crearEquipo) {
        Usuario usuario = new Usuario();
        usuario.setUsername(crearEquipo.getUsername());
        usuario.setCorreo(crearEquipo.getCorreo());
        usuario.setPassword(crearEquipo.getPassword());
        usuario.setRol(Rol.ENTRENADOR);
        usuarioRepository.save(usuario);

        Entrenador entrenador = new Entrenador();
        entrenador.setNombre(crearEquipo.getNombreEntrenador());
        entrenador.setApellido(crearEquipo.getApellido());
        entrenador.setFechaNacimiento(crearEquipo.getFechaNacimiento());
        entrenador.setImagen(crearEquipo.getImagenEntrenador());
        entrenador.setDni(crearEquipo.getDni());
        entrenador.setUsuario(usuario);
        entrenadorRepository.save(entrenador);

        Equipo equipo = new Equipo();
        equipo.setNombre(crearEquipo.getNombre());
        equipo.setDescripcion(crearEquipo.getDescripcion());
        equipo.setFechaFundacion(crearEquipo.getFechaFundacion());
        equipo.setImagen(crearEquipo.getImagen());
        equipo.setEntrenador(entrenador);

        return equipoRepository.save(equipo);
    }

    public Equipo update(Integer idEquipo, CrearEquipo crearEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        equipo.setNombre(crearEquipo.getNombre());
        equipo.setDescripcion(crearEquipo.getDescripcion());
        equipo.setFechaFundacion(crearEquipo.getFechaFundacion());
        equipo.setImagen(crearEquipo.getImagen());

        Entrenador entrenador = equipo.getEntrenador();
        entrenador.setNombre(crearEquipo.getNombreEntrenador());
        entrenador.setApellido(crearEquipo.getApellido());
        entrenador.setFechaNacimiento(crearEquipo.getFechaNacimiento());
        entrenador.setImagen(crearEquipo.getImagenEntrenador());
        entrenador.setDni(crearEquipo.getDni());
        entrenadorRepository.save(entrenador);

        return equipoRepository.save(equipo);
    }


    public void delete(Integer idEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        equipoRepository.delete(equipo);
    }




}
