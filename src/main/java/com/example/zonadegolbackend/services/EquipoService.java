package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.dtos.CrearEquipo;
import com.example.zonadegolbackend.entity.*;
import com.example.zonadegolbackend.enums.Rol;
import com.example.zonadegolbackend.repository.*;
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
    private final LigaRepository ligaRepository;
    private final LigaEquipoRepository ligaEquipoRepository;
    private final TemporadaRepository temporadaRepository;
    private final EquipoTemporadaRepository equipoTemporadaRepository;

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

    @Transactional
    public void associateTeamsWithLeague(Integer idLiga, List<Integer> idEquipos, Integer idTemporada) {
        Liga liga = ligaRepository.findById(idLiga)
                .orElseThrow(() -> new RuntimeException("Liga no encontrada"));

        Temporada temporada = temporadaRepository.findById(idTemporada)
                .orElseThrow(() -> new RuntimeException("Temporada no encontrada"));

        for (Integer idEquipo : idEquipos) {
            Equipo equipo = equipoRepository.findById(idEquipo)
                    .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

    public Equipo findByEntrenador(Integer idEntrenador) {
        Entrenador entrenador = entrenadorRepository.findById(idEntrenador)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));
        return equipoRepository.findByEntrenador(entrenador);
    }



            EquipoLiga equipoLiga = new EquipoLiga();
            equipoLiga.setLiga(liga);
            equipoLiga.setEquipo(equipo);
            equipoLiga.setTemporada(temporada);
            ligaEquipoRepository.save(equipoLiga);
        }
    }

    @Transactional
    public void associateTemporadasWithTeam(Integer idTemporada, List<Integer> idEquipos) {
        Temporada temporada = temporadaRepository.findById(idTemporada)
                .orElseThrow(() -> new RuntimeException("Temporada no encontrada"));

        for (Integer idEquipo : idEquipos) {
            Equipo equipo = equipoRepository.findById(idEquipo)
                    .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

            EquipoTemporada equipoTemporada = new EquipoTemporada();
            equipoTemporada.setEquipo(equipo);
            equipoTemporada.setTemporada(temporada);
            equipoTemporadaRepository.save(equipoTemporada);
        }
    }
}
