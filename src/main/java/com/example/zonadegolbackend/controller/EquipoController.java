package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.dtos.*;
import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Jugador;
import com.example.zonadegolbackend.entity.Temporada;
import com.example.zonadegolbackend.entity.Jornada;
import com.example.zonadegolbackend.services.EquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/equipo")
public class EquipoController {

    private final EquipoService equipoService;

    @GetMapping("/listar")
    public List<Equipo> listarEquipos() {
        return equipoService.findAll();
    }

    @PostMapping("/crear")
    public Equipo crearEquipo(@RequestBody CrearEquipo equipo) {
        return equipoService.create(equipo);
    }

    @PutMapping("/editar/{idEquipo}")
    public Equipo editarEquipo(@PathVariable Integer idEquipo, @RequestBody Equipo equipo) {
        return equipoService.update(idEquipo, equipo);
    }

//    @PostMapping("/asociarEquiposConLiga")
//    public void asociarEquiposConLiga(@RequestBody AsociarEquiposLigaDTO request) {
//        equipoService.associateTeamsWithLeague(request.getIdLiga(), request.getIdEquipos(), request.getIdTemporada());
//    }
//
//    @PostMapping("/asociarTemporadaConEquipo/{idEquipo}/temporada")
//    public void asociarTemporadaConEquipo(@PathVariable Integer idEquipo, @RequestBody List<Integer> idTemporada) {
//        equipoService.associateTemporadasWithTeam(idEquipo, idTemporada);
//    }

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



    @GetMapping("/jornadas")
    public List<JornadaDTO> obtenerJornadasEquipoLogueado() {
        List<Jornada> jornadas = equipoService.obtenerJornadasDelEquipoLogueado();

        if (jornadas != null && !jornadas.isEmpty()) {
            return jornadas.stream()
                    .map(JornadaDTO::new)
                    .toList();
        } else {
            throw new RuntimeException("No se encontraron jornadas para el equipo logueado");
        }
    }


//    @GetMapping("/jugadores")
//    public ResponseEntity<List<Jugador>> obtenerJugadoresDelEquipo() {
//        try {
//            List<Jugador> jugadores = equipoService.getEquipoJugadores();
//            return ResponseEntity.ok(jugadores);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }

}
