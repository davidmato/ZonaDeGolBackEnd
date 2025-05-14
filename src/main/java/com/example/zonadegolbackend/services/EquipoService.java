package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.dtos.CrearEquipo;
import com.example.zonadegolbackend.dtos.EquipoInfoDTO;
import com.example.zonadegolbackend.dtos.TemporadaDTO;
import com.example.zonadegolbackend.entity.*;
import com.example.zonadegolbackend.enums.Rol;
import com.example.zonadegolbackend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EquipoService {

    private final EquipoRepository equipoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EntrenadorRepository entrenadorRepository;
    private final LigaRepository ligaRepository;
    private final TemporadaRepository temporadaRepository;
    private final JugadorRepository jugadorRepository;
    private final TemporadaService temporadaService;
    private final ClasificacionRepository clasificacionRepository;
    private final JornadaRepository jornadaRepository;

    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }



    public Equipo create(CrearEquipo crearEquipo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.ENTRENADOR) {
            throw new RuntimeException("Solo un entrenador puede crear un equipo");
        }

        Entrenador entrenador = entrenadorRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        Liga liga = ligaRepository.findById(crearEquipo.getIdLiga())
                .orElseThrow(() -> new RuntimeException("Liga no encontrada"));

        Equipo equipo = new Equipo();
        equipo.setNombre(crearEquipo.getNombre());
        equipo.setDescripcion(crearEquipo.getDescripcion());
        equipo.setFechaFundacion(crearEquipo.getFechaFundacion());
        equipo.setImagen(crearEquipo.getImagen());
        equipo.setEntrenador(entrenador);
        equipo.setLiga(liga);
        equipoRepository.save(equipo);

        Clasificacion clasificacion = new Clasificacion();
        clasificacion.setPuesto(0);
        clasificacion.setPuntos(0);
        clasificacion.setPartidosJugados(0);
        clasificacion.setGolDiferencia(0);
        clasificacion.setGolAFavor(0);
        clasificacion.setGolEnContra(0);
        clasificacion.setVictorias(0);
        clasificacion.setEmpates(0);
        clasificacion.setDerrotas(0);
        clasificacion.setEquipo(equipo);
        clasificacion.setTemporada(temporadaService.buscarTemporadaMasReciente());
        clasificacionRepository.save(clasificacion);

        return equipo;
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


    public Equipo findByEntrenador(Integer idEntrenador) {
        Entrenador entrenador = entrenadorRepository.findById(idEntrenador)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));
        return equipoRepository.findByEntrenador(entrenador);
    }


    public List<Jugador> getJugadoresDelEquipo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.ENTRENADOR) {
            throw new RuntimeException("Solo un entrenador puede ver los jugadores de su equipo");
        }

        Entrenador entrenador = entrenadorRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        Equipo equipo = equipoRepository.findByEntrenador(entrenador);
        if (equipo == null) {
            throw new RuntimeException("El entrenador no tiene un equipo");
        }

        return jugadorRepository.findByEquipo(equipo);
    }


    public EquipoInfoDTO findByIdEquipoDTO(Integer idEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        EquipoInfoDTO dto = new EquipoInfoDTO();
        dto.setId(equipo.getId());
        dto.setNombre(equipo.getNombre());
        dto.setDescripcion(equipo.getDescripcion());
        dto.setFechaFundacion(equipo.getFechaFundacion());
        dto.setImagen(equipo.getImagen());
        dto.setEntrenadorNombre(equipo.getEntrenador().getNombre() + " " + equipo.getEntrenador().getApellido());
        dto.setLigaNombre(equipo.getLiga().getNombre());
        dto.setEntrenadorImagen(equipo.getEntrenador().getImagen());

        return dto;
    }


    public Integer obtenerLigaPorEquipo(Integer idEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        return equipo.getLiga().getId();
    }

    public List<TemporadaDTO> obtenerTemporadasPorEquipoDTO(Integer idEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        List<Clasificacion> clasificaciones = clasificacionRepository.findByEquipoId(equipo.getId());
        List<TemporadaDTO> temporadasDTO = new ArrayList<>();

        for (Clasificacion clasificacion : clasificaciones) {
            Temporada temporada = clasificacion.getTemporada();
            TemporadaDTO temporadaDTO = new TemporadaDTO();
            temporadaDTO.setId(temporada.getId());
            temporadaDTO.setNombre(temporada.getFechaInicio().getYear() + " - " + temporada.getFechaFin().getYear());

            if (!temporadasDTO.contains(temporadaDTO)) {
                temporadasDTO.add(temporadaDTO);
            }
        }

        return temporadasDTO;
    }

    public List<Jornada> obtenerJornadasDelEquipoLogueado() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Equipo equipo = equipoRepository.findByEntrenador_Usuario(usuario);
        if (equipo == null) {
            throw new RuntimeException("El usuario no está asociado a un equipo");
        }

        return jornadaRepository.findByEquipoLocalOrEquipoVisitante(equipo, equipo);
    }


//    public List<Jugador> getEquipoJugadores() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        Usuario usuario = usuarioRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
//
//        Entrenador entrenador = entrenadorRepository.findByUsuario(usuario)
//                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));
//
//        Equipo equipo = equipoRepository.findByEntrenador(entrenador);
//        if (equipo == null) {
//            throw new RuntimeException("El entrenador no tiene un equipo");
//        }
//
//        return jugadorRepository.findByEquipo(equipo);
//    }
}
