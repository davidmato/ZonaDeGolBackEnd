package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.entity.*;
import com.example.zonadegolbackend.dtos.*;
import com.example.zonadegolbackend.repository.EntrenadorRepository;
import com.example.zonadegolbackend.repository.EquipoRepository;
import com.example.zonadegolbackend.repository.JugadorRepository;
import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Jugador;
import com.example.zonadegolbackend.entity.Jornada;
import com.example.zonadegolbackend.services.EquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/equipo")
public class EquipoController {

    private final EquipoService equipoService;
    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;
    private final EntrenadorRepository entrenadorRepository;

    @GetMapping("/listar")
    public List<EquipoInfoDTO> listarEquipos() {
        return equipoService.listarEquipos();
    }

    @PostMapping("/crear")
    public Equipo crearEquipo(@RequestBody CrearEquipo equipo) {
        return equipoService.create(equipo);
    }

    @PutMapping("/editar/{idEquipo}")
    public Equipo editarEquipo(@PathVariable Integer idEquipo, @RequestBody Equipo equipo) {
        return equipoService.update(idEquipo, equipo);
    }


    @DeleteMapping("/eliminar/{idEquipo}")
    public void eliminarEquipo(@PathVariable Integer idEquipo) {
        equipoService.delete(idEquipo);
    }


    @GetMapping("/buscar/entrenador/{idEntrenador}")
    public Equipo buscarPorEntrenador(@PathVariable Integer idEntrenador) {
        return equipoService.findByEntrenador(idEntrenador);
    }


    @GetMapping("/buscar/liga/{idEquipo}")
    public Integer obtenerLigaPorEquipo(@PathVariable Integer idEquipo) {
        return equipoService.obtenerLigaPorEquipo(idEquipo);
    }

    @GetMapping("/listar/jugadores")
    public List<Jugador> getJugadoresDelEquipo() {
        return equipoService.getJugadoresDelEquipo();
    }

    @GetMapping("buscar/{idEquipo}")
    public EquipoInfoDTO buscarPorId(@PathVariable Integer idEquipo) {
        return equipoService.findByIdEquipoDTO(idEquipo);
    }

    @GetMapping("/buscar/temporada/{idEquipo}")
    public List<TemporadaDTO> obtenerTemporadasPorEquipo(@PathVariable Integer idEquipo) {
        return equipoService.obtenerTemporadasPorEquipoDTO(idEquipo);
    }


    @GetMapping("/jugadores/{idEquipo}")
    public List<JugadorDTO> obtenerJugadoresPorEquipo(@PathVariable Integer idEquipo) {
        return equipoService.obtenerJugadoresPorEquipo(idEquipo);
    }

    @GetMapping("/jornadas")
    public List<JornadaConIdsDTO> obtenerJornadasEquipoLogueado() {
        List<Jornada> jornadas = equipoService.obtenerJornadasDelEquipoLogueado();

        if (jornadas != null && !jornadas.isEmpty()) {
            return jornadas.stream()
                    .map(JornadaConIdsDTO::new)
                    .toList();
        } else {
            throw new RuntimeException("No se encontraron jornadas para el equipo logueado");
        }
    }



    @GetMapping("/buscar/jugadores/equipo/{idEquipo}")
    public List<Jugador> getJugadoresPorEquipo(@PathVariable Integer idEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        return jugadorRepository.findByEquipo(equipo);
    }

    @GetMapping("/entrenador/usuario/{userId}")
    public Entrenador getEntrenadorPorUserId(@PathVariable Integer userId) {
        return entrenadorRepository.findByUsuario_Id(userId)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));
    }



}
