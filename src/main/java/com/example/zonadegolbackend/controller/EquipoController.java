package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.dtos.AsociarEquiposLigaDTO;
import com.example.zonadegolbackend.dtos.CrearEquipo;
import com.example.zonadegolbackend.dtos.EquipoInfoDTO;
import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Jugador;
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
    public Equipo editarEquipo(@PathVariable Integer idEquipo, @RequestBody CrearEquipo equipo) {
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

    @GetMapping("/listar/jugadores")
    public List<Jugador> getJugadoresDelEquipo() {
        return equipoService.getJugadoresDelEquipo();
    }

    @GetMapping("buscar/{idEquipo}")
    public EquipoInfoDTO buscarPorId(@PathVariable Integer idEquipo) {
        return equipoService.findByIdEquipoDTO(idEquipo);
    }


    @GetMapping("/jornadas/{idEquipo}")
    public List<Jornada> obtenerJornadasPorEquipo(@PathVariable Integer idEquipo) {
        return equipoService.obtenerJornadasPorEquipo(idEquipo);
    }



}
