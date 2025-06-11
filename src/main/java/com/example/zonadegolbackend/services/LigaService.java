package com.example.zonadegolbackend.services;


import com.example.zonadegolbackend.entity.*;
import com.example.zonadegolbackend.enums.Rol;
import com.example.zonadegolbackend.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LigaService {

    private final LigaRepository ligaRepository;
    private final TrofeoRepository trofeoRepository;
    private final EquipoRepository equipoRepository;
    private final TemporadaRepository temporadaRepository;
    private final ClasificacionRepository clasificacionRepository;
    private final TemporadaLigaRepository temporadaLigaRepository;
    private final ClasificacionService clasificacionService;
    private final TemporadaService temporadaService;
    private final UsuarioRepository usuarioRepository;

    public List<Liga> findAll() {
        return ligaRepository.findAll();
    }

    public Liga findById(Integer id) {
        return ligaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Liga no encontrada"));
    }

    public List<Equipo> findByLigaId(Integer ligaId) {
        return equipoRepository.findByLigaId(ligaId);
    }

//    @Transactional
//    public Liga crearLiga(Liga liga) {
//
//        Trofeo trofeo = liga.getTrofeo();
//        if (trofeo != null) {
//            trofeoRepository.save(trofeo);
//        }
//        return ligaRepository.save(liga);
//    }

    public Liga crearLiga(Liga liga){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador crear una liga");
        }

        Liga nuevaLiga = new Liga();

        nuevaLiga.setNombre(liga.getNombre());
        nuevaLiga.setNumEquipos(liga.getNumEquipos());
        nuevaLiga.setDescripcion(liga.getDescripcion());
        nuevaLiga.setFecha_fundacion(liga.getFecha_fundacion());
        nuevaLiga.setImagen(liga.getImagen());
        ligaRepository.save(nuevaLiga);

        Temporada temporadaActual = temporadaService.buscarTemporadaMasReciente();

        TemporadaLiga temporadaLiga = new TemporadaLiga();
        temporadaLiga.setLiga(nuevaLiga);
        temporadaLiga.setTemporada(temporadaActual);
        temporadaLigaRepository.save(temporadaLiga);

        Trofeo trofeo = new Trofeo();
        trofeo.setNombre("Trofeo de " + nuevaLiga.getNombre() + " " + temporadaActual.getFechaInicio().getYear());
        trofeo.setImagen("default-trophy.png"); // Puedes cambiar esto por una imagen real
        trofeo.setTemporadaLiga(temporadaLiga);
        trofeoRepository.save(trofeo);

        return nuevaLiga;
    }

    public Liga editarLiga(Integer id, Liga liga){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede editar una liga");
        }

        Liga ligaExistente = ligaRepository.findById(id).orElseThrow(() -> new RuntimeException("Liga no encontrada"));
        ligaExistente.setNombre(liga.getNombre());
        ligaExistente.setNumEquipos(liga.getNumEquipos());
        ligaExistente.setDescripcion(liga.getDescripcion());
        ligaExistente.setFecha_fundacion(liga.getFecha_fundacion());

        return ligaRepository.save(ligaExistente);
    }

    public void eliminarLiga(Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede eliminar una liga");
        }

        Liga ligaExistente = ligaRepository.findById(id).orElseThrow(() -> new RuntimeException("Liga no encontrada"));

        ligaRepository.delete(ligaExistente);
    }

    public List<Clasificacion> obtenerClasificacionLigaTemporadaReciente(Integer ligaId) {
        Temporada temporadaReciente = temporadaRepository.findLatest().getFirst();
        clasificacionService.actualizarPuestosYObtenerClasificacion(ligaId, temporadaReciente.getId());
        return clasificacionRepository.findByEquipo_Liga_IdAndTemporada_Id(ligaId, temporadaReciente.getId());
    }

}
